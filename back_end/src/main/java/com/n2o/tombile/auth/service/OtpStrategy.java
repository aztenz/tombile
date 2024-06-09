package com.n2o.tombile.auth.service;

import com.n2o.tombile.auth.model.entity.User;
import com.n2o.tombile.enums.OtpType;

public interface OtpStrategy {
    String OTP_SENT_FOR_VERIFICATION = "otp sent for verification";

    default String sendOtp(String email, OtpType otpType) {
        User user = getTargetUser(email);

        getOtpService().sendOtpForVerification(user, otpType);

        return OTP_SENT_FOR_VERIFICATION;
    }

    default String verifyOtp(String email, int otp) {
        User user = getTargetUser(email);

        getOtpService().verifyOtp(otp, user);

        return getRoleStrategyFactory()
                .getStrategy(user.getUserData().getRole())
                .handleAfterVerifyEmail(user);
    }

    User getTargetUser(String email);

    OtpService getOtpService();

    RoleStrategyFactory getRoleStrategyFactory();
}
