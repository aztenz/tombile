package com.n2o.tombile.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final TokenService tokenService;

    @Value("${security.JwtService.SECRET_KEY}")
    private String secretKey;

    @Value("${security.JwtService.TOKEN_EXPIRATION_DURATION}")
    private int tokenExpirationDuration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public <T> T extractClaim(String token, ClaimsResolver<T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.resolve(claims);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpirationDuration))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public void handleExpiredToken(HttpServletResponse response, String token) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", "Token has expired");
        responseBody.put("message", "Your session has expired. Please log in again.");
        response.setContentType("application/json");
        tokenService.revokeTokenByToken(token);
        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @FunctionalInterface
    public interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }
}
