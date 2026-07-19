package com.utp.patrocinapp.infrastructure.security;

import com.utp.patrocinapp.domain.service.TokenServicePort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService implements TokenServicePort {
    private final SecretKey key;
    private final long expirationMs;

    public JwtService(@Value("${patrocinapp.jwt.secret}") String secret,
                      @Value("${patrocinapp.jwt.expiration-ms}") long expirationMs) {
        if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalStateException("JWT_SECRET debe contener al menos 32 bytes.");
        }
        if (expirationMs <= 0) {
            throw new IllegalStateException("JWT_EXPIRATION debe ser un número positivo.");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
    }

    @Override
    public String generar(String correo) {
        Date emitido = new Date();
        return Jwts.builder()
                .subject(correo)
                .issuedAt(emitido)
                .expiration(new Date(emitido.getTime() + expirationMs))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return claims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = claims(token);
            return claims.getSubject() != null && claims.getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    private Claims claims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
}
