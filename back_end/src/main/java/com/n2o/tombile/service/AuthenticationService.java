package com.n2o.tombile.service;

import com.n2o.tombile.dto.request.auth.LoginUserDTO;
import com.n2o.tombile.dto.request.auth.RegisterUserDTO;
import com.n2o.tombile.dto.response.auth.AuthenticationDTO;
import com.n2o.tombile.enums.TokenType;
import com.n2o.tombile.exception.DuplicateItemException;
import com.n2o.tombile.exception.ItemNotFoundException;
import com.n2o.tombile.model.User;
import com.n2o.tombile.model.UserData;
import com.n2o.tombile.repository.UserRepository;
import com.n2o.tombile.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {
    private static final String USER_NOT_FOUND = "Couldn't Find User: ";
    private static final String USERNAME_ALREADY_EXISTS = "Username already exists: ";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationDTO register(RegisterUserDTO request) {
        validateDuplicateUser(request.getUsername());

        User user = createUser(request);
        UserData userData = createUserData(request);

        user.setUserData(userData);
        userData.setUser(user);

        userRepository.save(user);

        return generateAuthenticationToken(user);
    }

    public AuthenticationDTO login(LoginUserDTO request) {
        authenticateUser(request.getUsername(), request.getPassword());

        User user = getUser(request.getUsername());

        user.getUserData().setLastLoginDate(new Date());

        userRepository.save(user);

        tokenService.revokeTokenById(user.getId());

        return generateAuthenticationToken(user);
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ItemNotFoundException(USER_NOT_FOUND + username));
    }

    private void validateDuplicateUser(String username) {
        userRepository.findByUsername(username).ifPresent(u -> {
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
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private AuthenticationDTO generateAuthenticationToken(User user) {
        String jwtToken = jwtService.generateToken(user.getUsername());

        tokenService.saveToken(jwtToken, user, TokenType.BEARER);

        String userRole = user.getUserData().getRole().name();

        return new AuthenticationDTO(jwtToken, userRole);
    }
}
