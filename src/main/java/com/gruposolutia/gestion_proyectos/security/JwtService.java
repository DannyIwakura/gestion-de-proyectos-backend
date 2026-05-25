package com.gruposolutia.gestion_proyectos.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET =
            "mi_clave_super_secreta_para_jwt_2026_proyecto_seguro";

    private static final long PRE_AUTH_EXPIRATION = 300000;

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }

    public String generatePreAuthToken(String username) {
        return Jwts.builder()
                .subject(username)
                .claim("type", "PRE_AUTH")
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis() + PRE_AUTH_EXPIRATION))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isPreAuthToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return "PRE_AUTH".equals(claims.get("type"));
        } catch (Exception e) {
            return false;
        }
    }
}
