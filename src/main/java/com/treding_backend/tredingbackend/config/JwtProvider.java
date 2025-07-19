////package com.treding_backend.tredingbackend.config;
////
////import io.jsonwebtoken.Claims;
////import io.jsonwebtoken.Jwts;
////import io.jsonwebtoken.security.Keys;
////import org.springframework.security.core.Authentication;
////import org.springframework.security.core.GrantedAuthority;
////
////import javax.crypto.SecretKey;
////import java.nio.charset.StandardCharsets;
////import java.util.Collection;
////import java.util.Date;
////import java.util.HashSet;
////import java.util.Set;
////import java.util.Base64;
////
////public class JwtProvider {
////
////    // Corrected SECRET_KEY reference and decoding
//////    private static final String SECRET_KEY = "ztOIm56lZ2RkXbhT7QzJr1M/NxdKAgjVxxmS1zHkPQE=";
//////    private static final SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET_KEY));
////
////
////    static String secret = "a-string-secret-at-least-256-bits-long";
////    static SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
////
////    public static String generateToken(Authentication auth) {
////        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
////        String roles = populateAuthorities(authorities);
////
////        return Jwts.builder()
////                .setIssuedAt(new Date())
////                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
////                .claim("email", auth.getName())
////                .claim("Authorities", roles)
////                .signWith(key)
////                .compact();
////    }
////
////    // Get email from JWT token
////    public static String getEmailFromToken(String token) {
////        if (token.startsWith("Bearer")) {
////            token = token.substring(7);
////        }
////
////        Claims claims = Jwts.parserBuilder()
////                .setSigningKey(key)
////                .build()
////                .parseClaimsJws(token)
////                .getBody();
////
////        return String.valueOf(claims.get("email")); // Fixed incorrect claim key
////    }
////
////    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
////        Set<String> auth = new HashSet<>();
////        for (GrantedAuthority ga : authorities) {
////            auth.add(ga.getAuthority());
////        }
////        return String.join(",", auth);
////    }
////}
//
//
//package com.treding_backend.tredingbackend.config;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.util.*;
//
//public class JwtProvider {
//
//    // ✅ Your secret key should be at least 32 characters for HS256
//
//    private static final String SECRET = "a-string-secret-at-least-256-bits-long";
//  private static final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
//
////   private static final SecretKey key=   Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.getSecret());
//
//
//
//    // ✅ Generate JWT Token with authorities and email
//    public static String generateToken(Authentication auth) {
//        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
//        String roles = populateAuthorities(authorities);
//
//        return Jwts.builder()
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 604800000)) // 1 day expiration
//                .claim("email", auth.getName())
//                .claim("Authorities", roles) // ✅ lowercase "authorities"
//                .signWith(key)
//                .compact();
//    }
//
//    // ✅ Extract email from token
//    public static String getEmailFromToken(String token) {
//        if (token.startsWith("Bearer ")) {
//            token = token.substring(7).trim(); // remove Bearer and whitespace
//        }
//
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//        return String.valueOf(claims.get("email")); // ✅ matches claim name in generateToken
//    }
//
//    // ✅ Convert authorities list to comma-separated string
//    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
//        Set<String> auth = new HashSet<>();
//        for (GrantedAuthority ga : authorities) {
//            auth.add(ga.getAuthority());
//        }
//        return String.join(",", auth);
//    }
//}
//


package com.treding_backend.tredingbackend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private static SecretKey key;

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @PostConstruct
    public void init() {
        // Decode base64 secret key
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.getSecret()));
    }

    public static String generateToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = String.join(",",
                authorities.stream().map(GrantedAuthority::getAuthority).toList());

        return Jwts.builder()

                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JwtProperties.getExpiration()))
                .claim("email", auth.getName())
                .claim("Authorities", roles)
                .signWith(key)
                .compact();
    }

    public static String getEmailFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return String.valueOf(claims.get("email"));
    }
}

