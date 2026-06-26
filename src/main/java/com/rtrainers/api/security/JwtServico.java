package com.rtrainers.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtServico {

    @Value("${jwt.secret}")
    private String segredo;

    @Value("${jwt.expiration-ms}")
    private Long expiracaoMs;

    private SecretKey obterChave() {
        return Keys.hmacShaKeyFor(segredo.getBytes());
    }

    public String gerarToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiracaoMs))
                .signWith(obterChave())
                .compact();
    }

    public String extrairEmail(String token) {
        return extrairClaims(token).getSubject();
    }

    public boolean validarToken(String token) {
        try {
            extrairClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extrairClaims(String token) {
        return Jwts.parser()
                .verifyWith(obterChave())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}