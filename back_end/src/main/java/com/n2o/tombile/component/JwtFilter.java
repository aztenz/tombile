package com.n2o.tombile.component;

import com.n2o.tombile.repository.TokenRepository;
import com.n2o.tombile.service.JwtService;
import com.n2o.tombile.service.UserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String token = extractTokenFromHeader(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        //TODO: check tokenization logic -> if the token expired, what is going to happen, cause it throws an exception
        String username = getUsernameFromToken(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            authenticateUser(token, username, request);
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private String getUsernameFromToken(String token) {
        return jwtService.extractUsername(token);
    }

    private void authenticateUser(String token, String username, HttpServletRequest request) {
        UserDetails userDetails = loadUserByUsername(username);
        if (isValidToken(token) && isTokenValidInDB(token)) {
            setAuthentication(userDetails, request);
        }
    }

    private UserDetails loadUserByUsername(String username) {
        return userDetailsService.loadUserByUsername(username);
    }

    private boolean isValidToken(String token) {
        UserDetails userDetails = loadUserByUsername(getUsernameFromToken(token));
        userDetails.getPassword();
        return jwtService.isValid(token, userDetails);
    }

    private boolean isTokenValidInDB(String token) {
        return tokenRepository.findByToken(token)
                .map(t -> !t.getExpired() && !t.getRevoked())
                .orElse(false);
    }

    private void setAuthentication(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
