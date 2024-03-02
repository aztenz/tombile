package com.n2o.tombile.service;

import com.n2o.tombile.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY_PATH =
            "${security.JwtService.SECRET_KEY}";
    private static final String TOKEN_EXPIRATION_DURATION_PATH =
            "${security.JwtService.TOKEN_EXPIRATION_DURATION}";
    @Value(TOKEN_EXPIRATION_DURATION_PATH)
    private int TOKEN_EXPIRATION_DURATION;

    @Value(SECRET_KEY_PATH)
    private String SECRET_KEY;

    public <T> T extractClaim(
            String token,
            Function<Claims, T> resolver
    ) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public boolean isValid(
            String token,
            UserDetails user
    ) {
        String username = extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(User user) {
        return Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_DURATION))
                .signWith(getSignInKey())
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token)
                .before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
