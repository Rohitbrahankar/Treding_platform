//package com.treding_backend.tredingbackend.config;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.crypto.SecretKey;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//import static org.yaml.snakeyaml.tokens.Token.ID.Key;
//
//public class JwtTokenValidator extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String jwt=request.getHeader(JwtConstant.JWT_HEADER);
//
//
//        if(jwt!=null)
//        {
//            jwt=jwt.substring(7);
//
//            try{
//                SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECREATE_KEY.getBytes());
//                Claims claims= Jwts.parserBuilder().setSigningKey(String.valueOf(Key)).build().parseClaimsJws(jwt).getBody();
//                String  email= String.valueOf(claims .get("Email"));
//
//                String authorities = String.valueOf(claims.get("authorities"));
//
//                List<GrantedAuthority>authorityList= AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
//
//               Authentication auth= new UsernamePasswordAuthenticationToken(
//                       email,
//                       null,
//                       authorityList
//               );
//                SecurityContextHolder.getContext().setAuthentication(auth);
//
//
//
//            }catch (Exception e)
//            {
//                throw new RuntimeException("Invalid token......");
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//}

package com.treding_backend.tredingbackend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if (jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7); // remove "Bearer " prefix

            try {
                String path = request.getRequestURI();
                if (path.startsWith("/auth")) {
                    filterChain.doFilter(request, response); // skip JWT check
                    return;
                }

//                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECREATE_KEY.getBytes());
//                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8));
                SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(JwtProperties.getSecret()));


                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("Authorities"));

                List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                Authentication auth = new UsernamePasswordAuthenticationToken(email, null, authorityList);
                SecurityContextHolder.getContext().setAuthentication(auth);

            }  catch (Exception e) {
                e.printStackTrace(); // ✅ See what exactly went wrong
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token: " + e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}

