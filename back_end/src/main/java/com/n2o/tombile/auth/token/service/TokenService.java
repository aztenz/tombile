package com.n2o.tombile.auth.token.service;

import com.n2o.tombile.auth.token.model.TokenType;
import com.n2o.tombile.auth.token.model.Token;
import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.auth.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public void revokeTokenById(int userId) {
        tokenRepository.findById(userId).ifPresent(tokenRepository::delete);
    }

    public void revokeTokenByToken(String token) {
        tokenRepository.findByToken(token).ifPresent(tokenRepository::delete);
    }

    public void saveToken(String tokenString, User user, TokenType tokenType) {
        Token token = new Token();
        token.setTokenType(tokenType);
        token.setUser(user);
        token.setToken(tokenString);
        tokenRepository.save(token);
    }
}
