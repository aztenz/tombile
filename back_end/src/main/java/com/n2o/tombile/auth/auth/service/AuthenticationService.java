package com.n2o.tombile.auth.auth.service;

import com.n2o.tombile.auth.otp.dto.RQSendOtp;
import com.n2o.tombile.auth.otp.dto.RQVerifyOtp;
import com.n2o.tombile.auth.otp.model.OtpType;
import com.n2o.tombile.auth.otp.service.OtpService;
import com.n2o.tombile.auth.otp.service.OtpStrategyFactory;
import com.n2o.tombile.auth.token.dto.RSPToken;
import com.n2o.tombile.auth.token.model.TokenType;
import com.n2o.tombile.auth.token.service.JwtService;
import com.n2o.tombile.auth.token.service.TokenService;
import com.n2o.tombile.core.user.dto.RQLogin;
import com.n2o.tombile.core.user.dto.RQRegister;
import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.n2o.tombile.core.common.util.Constants.OTP_SENT;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final UserService userService;
    private final OtpService otpService;
    private final AuthenticationManager authenticationManager;
    private final OtpStrategyFactory otpStrategyFactory;


    public String register(RQRegister request) {
        User user = userService.createUser(request);

        otpService.sendOtpForVerification(user, OtpType.VERIFY_EMAIL);

        return OTP_SENT;
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
