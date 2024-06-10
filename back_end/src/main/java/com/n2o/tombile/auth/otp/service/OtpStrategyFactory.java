package com.n2o.tombile.auth.otp.service;

import com.n2o.tombile.core.user.service.RoleStrategyFactory;
import com.n2o.tombile.core.user.service.UserService;
import com.n2o.tombile.auth.otp.model.OtpType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OtpStrategyFactory {
    private final Map<OtpType, OtpStrategy> strategies;
    private final UserService userService;
    private final OtpService otpService;
    private final RoleStrategyFactory roleStrategyFactory;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        strategies.put(OtpType.VERIFY_EMAIL,
                new OtpStrategyVerifyEmail(userService, otpService, roleStrategyFactory));

        strategies.put(OtpType.RECOVER_PASSWORD,
                new OtpStrategyRecoverPassword(userService, otpService, roleStrategyFactory, passwordEncoder));
    }

    public OtpStrategy getStrategy(OtpType otpType) {
        return strategies.get(otpType);
    }
}
