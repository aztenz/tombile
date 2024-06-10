package com.n2o.tombile.auth.otp.service;

import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.core.user.service.RoleStrategyFactory;
import com.n2o.tombile.core.user.service.UserService;
import com.n2o.tombile.auth.otp.model.OtpType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpStrategyVerifyEmail implements OtpStrategy {
    private final UserService userService;
    private final OtpService otpService;
    private final RoleStrategyFactory roleStrategyFactory;

    @Override
    public User getTargetUser(String email) {
        return userService.getNonVerifiedUserByEmail(email);
    }

    @Override
    public OtpType getOtpType() {
        return OtpType.VERIFY_EMAIL;
    }

    @Override
    public OtpService getOtpService() {
        return otpService;
    }

    @Override
    public RoleStrategyFactory getRoleStrategyFactory() {
        return roleStrategyFactory;
    }
}
