package com.socio.socio.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component

// @Component is used for the class to be detected during component scanning and registered as a Spring bean.
public class JwtUtil {

    private final String SECRET = "862e87e351aa128586724843825d3cc5f0a8123527651cc35ba406533a8925ff";

    private final long EXPIRATION = 1000 * 60 * 60; // 1 hour

    public String generateToken(Long userId, String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}
