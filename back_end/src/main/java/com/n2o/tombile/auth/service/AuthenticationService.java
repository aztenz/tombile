package com.n2o.tombile.auth.service;

import com.n2o.tombile.auth.dto.RQLogin;
import com.n2o.tombile.auth.dto.RQRegister;
import com.n2o.tombile.auth.dto.RQVerifyEmail;
import com.n2o.tombile.auth.dto.RSPToken;
import com.n2o.tombile.auth.model.enums.TokenType;
import com.n2o.tombile.auth.model.enums.VerificationStatus;
import com.n2o.tombile.enums.OtpType;
import com.n2o.tombile.exception.DuplicateItemException;
import com.n2o.tombile.exception.ItemNotFoundException;
import com.n2o.tombile.auth.model.entity.User;
import com.n2o.tombile.auth.model.entity.UserData;
import com.n2o.tombile.auth.repository.UserRepository;
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
    private static final String USER_NOT_FOUND = "couldn't Find User: ";
    private static final String NON_VERIFIED_USER_NOT_FOUND = "couldn't Find non verified User: ";
    private static final String USERNAME_ALREADY_EXISTS = "username already exists: ";
    private static final String OTP_SENT_FOR_VERIFICATION = "otp sent for verification";
    private static final String EMAIL_VERIFIED = "email verified";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final OtpService otpService;
    private final AuthenticationManager authenticationManager;


    public String register(RQRegister request) {
        validateDuplicateUser(request.getUsername());

        User user = createUser(request);
        UserData userData = createUserData(request);

        user.setUserData(userData);
        userData.setUser(user);

        userRepository.save(user);

        otpService.sendOtpForVerification(user, OtpType.VERIFY_EMAIL);

        return OTP_SENT_FOR_VERIFICATION;
    }

    public RSPToken login(RQLogin request) {
        authenticateUser(request.getUsername(), request.getPassword());

        User user = getUser(request.getUsername());

        user.getUserData().setLastLoginDate(new Date());

        userRepository.save(user);

        tokenService.revokeTokenById(user.getId());

        return generateAuthenticationToken(user);
    }

    public String verifyEmail(RQVerifyEmail request) {
        User user = userRepository
                .findByEmailAndVerificationStatus(request.getEmail(), VerificationStatus.NOT_VERIFIED)
                .orElseThrow(() -> new ItemNotFoundException(NON_VERIFIED_USER_NOT_FOUND + request.getEmail()));
        otpService.verifyOtp(request.getOtp(), user);
        user.getUserData().setVerificationStatus(VerificationStatus.VERIFIED);
        return EMAIL_VERIFIED;
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

    private User createUser(RQRegister request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }

    private UserData createUserData(RQRegister request) {
        Date date = new Date();
        UserData userData = Util.cloneObject(request, UserData.class);
        userData.setLastLoginDate(date);
        userData.setRegistrationDate(date);
        userData.setVerificationStatus(VerificationStatus.NOT_VERIFIED);
        return userData;
    }

    private void authenticateUser(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private RSPToken generateAuthenticationToken(User user) {
        String jwtToken = jwtService.generateToken(user.getUsername());

        tokenService.saveToken(jwtToken, user, TokenType.BEARER);

        String userRole = user.getUserData().getRole().name();

        return new RSPToken(jwtToken, userRole);
    }
}
