package com.todoist.web.authentication.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    public String generateToken(String email,String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role",role)
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey())
                .compact();
    }

    public Claims extractInformation(String jwtString){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(jwtString).getPayload();
    }

    public String getEmail(String jwtString){
        return extractInformation(jwtString).getSubject();
    }

}