package com.n2o.tombile.auth.otp.service;

import com.n2o.tombile.auth.otp.dto.RQResetPassword;
import com.n2o.tombile.auth.otp.dto.RQVerifyOtp;
import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.core.user.service.RoleStrategyFactory;
import com.n2o.tombile.core.user.service.UserService;
import com.n2o.tombile.auth.otp.model.OtpType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpStrategyRecoverPassword implements OtpStrategy {
    private static final String PASSWORD_RESET_SUCCESSFULLY = "password reset successfully";
    private final UserService userService;
    private final OtpService otpService;
    private final RoleStrategyFactory roleStrategyFactory;
    private final PasswordEncoder passwordEncoder;
    private User user;

    @Override
    public User getTargetUser(String email) {
        user = userService.getUserByEmail(email);
        return user;
    }

    @Override
    public OtpType getOtpType() {
        return OtpType.RECOVER_PASSWORD;
    }

    @Override
    public OtpService getOtpService() {
        return otpService;
    }

    @Override
    public RoleStrategyFactory getRoleStrategyFactory() {
        return roleStrategyFactory;
    }

    @Override
    public <R extends RQVerifyOtp> String verifyOtp(R request) {
        OtpStrategy.super.verifyOtp(request);
        String newPassword = ((RQResetPassword) request).getNewPassword();
        user.setPassword(passwordEncoder.encode(newPassword));
        return PASSWORD_RESET_SUCCESSFULLY;
    }
}
