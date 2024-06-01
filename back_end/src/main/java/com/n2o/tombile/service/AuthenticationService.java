package com.n2o.tombile.service;

import com.n2o.tombile.dto.request.auth.LoginUserDTO;
import com.n2o.tombile.dto.request.auth.RegisterUserDTO;
import com.n2o.tombile.dto.response.auth.AuthenticationDTO;
import com.n2o.tombile.enums.Role;
import com.n2o.tombile.enums.TokenType;
import com.n2o.tombile.exception.DuplicateItemException;
import com.n2o.tombile.exception.ItemNotFoundException;
import com.n2o.tombile.model.Token;
import com.n2o.tombile.model.User;
import com.n2o.tombile.model.UserData;
import com.n2o.tombile.repository.TokenRepository;
import com.n2o.tombile.repository.UserRepository;
import com.n2o.tombile.util.Util;
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
    public static final String USER_NOT_FOUND = "Couldn't Find User: ";
    private static final String USERNAME_ALREADY_EXISTS = "Username already exists: ";
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
                () -> new ItemNotFoundException(USER_NOT_FOUND + request.getUsername())
        );

        revokeAllTokens(user);

        user.getUserData().setLastLoginDate(new Date());

        userRepository.save(user);

        return generateAuthenticationToken(request.getUsername());
    }

    private void validateDuplicateUser(String username) {
        userRepository.findByUsername(username)
                .ifPresent(u -> {
                    throw new DuplicateItemException(USERNAME_ALREADY_EXISTS + username);
                });
    }

    private User createUser(RegisterUserDTO request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }

    private UserData createUserData(RegisterUserDTO request) {
        Date date = new Date();
        UserData userData = Util.cloneObject(request, UserData.class);
        userData.setLastLoginDate(date);
        userData.setRegistrationDate(date);
        return userData;
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

        Token token = new Token();
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ItemNotFoundException(USER_NOT_FOUND + username));

        token.setUser(user);

        tokenRepository.save(token);

        String userRole = user.getUserData().getRole().name();

        return new AuthenticationDTO(jwtToken, userRole);
    }
}
