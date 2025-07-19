package com.treding_backend.tredingbackend.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ✅ Disable CSRF for stateless REST APIs
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ Enable CORS
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // ✅ Stateless session
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/auth/signin", "/auth/signup").permitAll()
                        .requestMatchers("/api/**").authenticated() // ✅ Secure /api/** endpoints
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class); // ✅ Add JWT filter

        return http.build();
    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
////        // ✅ Basic CORS config to allow frontend requests
////        CorsConfiguration config = new CorsConfiguration();
////        config.setAllowedOrigins(List.of("*")); // Replace * with your frontend origin in production
////        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
////        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
////        config.setAllowCredentials(true);
////
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", config);
//
//
//
//
//        return new CorsConfigurationSource() {
//            @Override
//            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//
//                CorsConfiguration config = new CorsConfiguration();
//                config.setAllowedOrigins(
//                        Arrays.asList(
//                                "http://localhost:5173", //when deploy frontend past deploy url
//                                "http://localhost:3000"
//                        )
//
//                );
//
//                config.setAllowedMethods(Collections.singletonList("*"));
//
//                config.setAllowCredentials(true);
//                config.setExposedHeaders(Arrays.asList("Authorization"));
//                config.setExposedHeaders(Collections.singletonList("*"));
//                config.setMaxAge(3600L);
//
//
//                return config;
//            }
//        };
//    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",  // ✅ Your frontend
                "http://localhost:3000"
        ));
//        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        config.setExposedHeaders(Arrays.asList("Authorization")); // optional

        // ✅ Correctly register the config to apply to all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }


}
