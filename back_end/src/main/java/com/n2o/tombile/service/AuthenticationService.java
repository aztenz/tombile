package com.n2o.tombile.service;

import com.n2o.tombile.dto.response.auth.AuthenticationDTO;
import com.n2o.tombile.dto.request.auth.LoginUserDTO;
import com.n2o.tombile.dto.request.auth.RegisterUserDTO;
import com.n2o.tombile.enums.TokenType;
import com.n2o.tombile.exception.DuplicateUserException;
import com.n2o.tombile.exception.UserNotFoundException;
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

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private static final double WALLET_BALANCE = 0.0;
    private static final String USERNAME_ALREADY_EXISTS = "Username already exists: ";
    public static final String USER_NOT_FOUND = "Couldn't Find User: ";

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationDTO register(RegisterUserDTO request) {
        validateDuplicateUser(request.getUsername());

        User user = createUser(request);
        UserData userData = createUserData(request);

        user.setUserData(userData);

        userRepository.save(user);

        return generateAuthenticationToken(request.getUsername());
    }

    public AuthenticationDTO login(LoginUserDTO request) {
        authenticateUser(request.getUsername(), request.getPassword());

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new UserNotFoundException(USER_NOT_FOUND + request.getUsername())
        );

        revokeAllTokens(user);

        return generateAuthenticationToken(request.getUsername());
    }

    private void validateDuplicateUser(String username) {
        userRepository.findByUsername(username)
                .ifPresent(u -> {
                    throw new DuplicateUserException(USERNAME_ALREADY_EXISTS + username);
                });
    }

    private User createUser(RegisterUserDTO request) {
        return User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    private UserData createUserData(RegisterUserDTO request) {
        Date date = new Date();
        return UserData.builder()
                .email(request.getEmail())
                .role(request.getRole())
                .walletBalance(WALLET_BALANCE)
                .verificationStatus(false)
                .registrationDate(date)
                .lastLoginDate(date)
                .build();
    }

    private void authenticateUser(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
    }

    private void revokeAllTokens(User user) {
        List<Token> validTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        validTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validTokens);
    }

    private AuthenticationDTO generateAuthenticationToken(String username) {
        String jwtToken = jwtService.generateToken(username);
        Token token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        token.setUser(userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND + username)));

        tokenRepository.save(token);

        return new AuthenticationDTO(jwtToken);
    }
}
