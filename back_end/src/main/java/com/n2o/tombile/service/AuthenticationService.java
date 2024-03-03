package com.n2o.tombile.service;

import com.n2o.tombile.dto.auth.AuthenticationDTO;
import com.n2o.tombile.dto.auth.LoginUserDTO;
import com.n2o.tombile.dto.auth.RegisterUserDTO;
import com.n2o.tombile.enums.TokenType;
import com.n2o.tombile.model.Token;
import com.n2o.tombile.model.User;
import com.n2o.tombile.model.UserData;
import com.n2o.tombile.repository.TokenRepository;
import com.n2o.tombile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationDTO register(RegisterUserDTO request) {
        Date date = new Date();
        String jwtToken = jwtService.generateToken(request.getUsername());

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .tokens(new ArrayList<>())
                .build();

        UserData userData = UserData.builder()
                .email(request.getUserData().getEmail())
                .role(request.getUserData().getRole())
                .walletBalance(0.0)
                .verificationStatus(false)
                .registrationDate(date)
                .lastLoginDate(date)
                .build();
        Token token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        token.setUser(user);
        user.getTokens().add(token);
        user.setUserData(userData);
        userRepository.save(user);

        return new AuthenticationDTO(jwtToken);
    }

    public AuthenticationDTO authenticate(LoginUserDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(request.getUsername());
        Token token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        token.setUser(user);
        user.getTokens().add(token);
        revokeAllTokens(user);
        tokenRepository.save(token);
        return new AuthenticationDTO(jwtToken);
    }

    private void revokeAllTokens(User user){
        List<Token> validTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if(validTokens.isEmpty()) return;
        validTokens.forEach(t-> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validTokens);
    }
}
