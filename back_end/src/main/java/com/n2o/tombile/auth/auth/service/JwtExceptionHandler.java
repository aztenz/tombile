package com.n2o.tombile.auth.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n2o.tombile.auth.token.service.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.n2o.tombile.core.common.util.Constants.ERROR_INVALID_TOKEN;
import static com.n2o.tombile.core.common.util.Constants.ERROR_SESSION_EXPIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_TOKEN_IS_EXPIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_TOKEN_IS_INVALID;

@Service
@RequiredArgsConstructor
public class JwtExceptionHandler {
    private final TokenService tokenService;

    public void handleExpiredToken(HttpServletResponse response, String token) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", ERROR_TOKEN_IS_EXPIRED);
        responseBody.put("message", ERROR_SESSION_EXPIRED);
        response.setContentType("application/json");
        tokenService.revokeTokenByToken(token);
        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
    }

    public void handleInvalidToken(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", ERROR_TOKEN_IS_INVALID);
        responseBody.put("message", ERROR_INVALID_TOKEN);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
    }
}
