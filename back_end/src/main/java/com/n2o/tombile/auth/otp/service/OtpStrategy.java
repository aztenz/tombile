package com.n2o.tombile.auth.otp.service;

import com.n2o.tombile.auth.otp.dto.RQVerifyOtp;
import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.core.user.service.RoleStrategyFactory;
import com.n2o.tombile.auth.otp.model.OtpType;

public interface OtpStrategy {
    String OTP_SENT_FOR_VERIFICATION = "otp sent for verification";

    default String sendOtp(String email, OtpType otpType) {
        User user = getTargetUser(email);

        getOtpService().sendOtpForVerification(user, otpType);

        return OTP_SENT_FOR_VERIFICATION;
    }

    default <R extends RQVerifyOtp> String verifyOtp(R request) {
        User user = getTargetUser(request.getEmail());

        getOtpService().verifyOtp(request.getOtp(), user, getOtpType());

        return getRoleStrategyFactory()
                .getStrategy(user.getUserData().getRole())
                .handleAfterVerifyEmail(user);
    }

    User getTargetUser(String email);

    OtpType getOtpType();

    OtpService getOtpService();

    RoleStrategyFactory getRoleStrategyFactory();
}
