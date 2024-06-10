package com.n2o.tombile.auth.service;

import com.n2o.tombile.auth.dto.*;
import com.n2o.tombile.auth.model.enums.TokenType;
import com.n2o.tombile.enums.OtpType;
import com.n2o.tombile.auth.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {
    private static final String OTP_SENT_FOR_VERIFICATION = "otp sent for verification";
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final UserService userService;
    private final OtpService otpService;
    private final AuthenticationManager authenticationManager;
    private final OtpStrategyFactory otpStrategyFactory;


    public String register(RQRegister request) {
        User user = userService.createUser(request);

        otpService.sendOtpForVerification(user, OtpType.VERIFY_EMAIL);

        return OTP_SENT_FOR_VERIFICATION;
    }

    public RSPToken login(RQLogin request) {
        authenticateUser(request.getUsername(), request.getPassword());

        User user = userService.findUserForLogin(request);

        tokenService.revokeTokenById(user.getId());

        return generateAuthenticationToken(user);
    }

    public String sendOtp(RQSendOtp request, OtpType otpType) {
        return otpStrategyFactory
                .getStrategy(otpType)
                .sendOtp(request.getEmail(), otpType);
    }

    public String verifyOtp(RQVerifyOtp request, OtpType otpType) {
        return otpStrategyFactory
                .getStrategy(otpType)
                .verifyOtp(request);
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
