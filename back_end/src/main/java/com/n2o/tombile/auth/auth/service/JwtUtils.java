package com.n2o.tombile.auth.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.SecretKey;
import java.security.SecureRandom;

import static com.n2o.tombile.core.common.util.Constants.JWT_SECRET_KEY_BYTE_SIZE;
import static com.n2o.tombile.core.common.util.Constants.JWT_SECRET_KEY_SIZE;

public class JwtUtils {
    private static final String secretKey = generateSecretKey();

    public static <T> T extractClaim(String token, ClaimsResolver<T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.resolve(claims);
    }

    public static Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static String extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    static SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private static String generateSecretKey() {
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
        return hexString.toString();
    }

    @FunctionalInterface
    public interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }
}
