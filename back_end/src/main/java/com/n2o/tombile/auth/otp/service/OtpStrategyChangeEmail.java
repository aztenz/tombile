package com.n2o.tombile.auth.otp.service;

import com.n2o.tombile.auth.otp.dto.RQVerifyOtp;
import com.n2o.tombile.auth.otp.model.Otp;
import com.n2o.tombile.auth.otp.model.OtpId;
import com.n2o.tombile.auth.otp.model.OtpType;
import com.n2o.tombile.core.common.util.Util;
import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.core.user.service.RoleStrategyFactory;
import com.n2o.tombile.core.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.n2o.tombile.core.common.util.Constants.EMAIL_CHANGE_SUCCESS;
import static com.n2o.tombile.core.common.util.Constants.OTP_SENT;

@Service
@RequiredArgsConstructor
public class OtpStrategyChangeEmail implements OtpStrategy {
    private final UserService userService;
    private final OtpService otpService;
    private final RoleStrategyFactory roleStrategyFactory;

    @Override
    public User getTargetUser(String email) {
        return Util.getCurrentUser();
    }

    @Override
    public OtpType getOtpType() {
        return OtpType.CHANGE_EMAIL;
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
    public String sendOtp(String email, OtpType otpType) {
        User user = Util.getCurrentUser();
        Otp otp = otpService.createOtp(user, OtpType.CHANGE_EMAIL);
        otp.setRequestBody(email);
        otpService.saveAndSendOtp(email, user.getUserData().getFirstName(), otp);
        return OTP_SENT;
    }

    @Override
    public <R extends RQVerifyOtp> String verifyOtp(R request) {
        OtpStrategy.super.verifyOtp(request);
        OtpId otpId = otpService.getOtpId(Util.getCurrentUserId(), OtpType.CHANGE_EMAIL);
        Otp otp = otpService.getOtpById(otpId);
        User user = Util.getCurrentUser();
        user.getUserData().setEmail(otp.getRequestBody());
        userService.saveChanges(user);
        return EMAIL_CHANGE_SUCCESS;
    }
}
