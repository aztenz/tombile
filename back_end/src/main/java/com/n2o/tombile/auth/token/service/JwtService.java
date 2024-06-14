package com.n2o.tombile.auth.token.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.n2o.tombile.core.common.util.Constants.JWT_SECRET_KEY_BYTE_SIZE;
import static com.n2o.tombile.core.common.util.Constants.JWT_SECRET_KEY_SIZE;
import static com.n2o.tombile.core.common.util.Constants.JWT_TOKEN_EXPIRATION_MILLISECONDS;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final TokenService tokenService;
    private static String secretKey;

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
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_EXPIRATION_MILLISECONDS))
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
        return Keys.hmacShaKeyFor(generateSecretKey().getBytes());
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

    private static String generateSecretKey() {
        if(secretKey != null) return secretKey;
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[JWT_SECRET_KEY_BYTE_SIZE];
        secureRandom.nextBytes(keyBytes);

        StringBuilder hexString = new StringBuilder(JWT_SECRET_KEY_SIZE);
        for (byte b : keyBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        secretKey = hexString.toString();
        return secretKey;
    }
}
