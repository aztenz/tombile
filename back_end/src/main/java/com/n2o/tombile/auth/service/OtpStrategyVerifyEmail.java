package com.n2o.tombile.auth.service;

import com.n2o.tombile.auth.model.entity.User;
import com.n2o.tombile.enums.OtpType;
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
