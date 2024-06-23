package com.n2o.tombile.auth.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.n2o.tombile.core.common.util.Constants.JWT_TOKEN_EXPIRATION_MILLISECONDS;

@Service
@RequiredArgsConstructor
public class JwtService {
    public String extractUsername(String token) {
        return JwtUtils.extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return JwtUtils.extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_EXPIRATION_MILLISECONDS))
                .signWith(JwtUtils.getSigningKey())
                .compact();
    }

    public boolean isValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
