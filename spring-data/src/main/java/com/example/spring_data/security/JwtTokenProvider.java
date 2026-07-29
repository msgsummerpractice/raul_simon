package com.example.spring_data.security;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private String jwtSecret;

    private Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    public JwtTokenProvider(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    private long jwtExpirationInMs = 3600000;

    public String generateToken(Authentication authentication) {
        String name = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        String token = Jwts.builder()
                .subject(name)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith((SecretKey) key())
                .compact();
        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String token){
        try{
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        }
        catch(JwtException ex){
            logger.error("Invalid JWT token: {}", ex.getMessage());
            return null;
        }
        catch(IllegalArgumentException ex){
            logger.error("JWT claims string is empty: {}", ex.getMessage());
            return null;
        }
    }

    public boolean validateToken(String token){
        try{
        Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parse(token);
        return true;
        }
        catch(JwtException ex){
            logger.error("Invalid JWT token: {}", ex.getMessage());
            return false;
        }
        catch(IllegalArgumentException ex){
            logger.error("JWT claims string is empty: {}", ex.getMessage());
            return false;
        }

    }
}
