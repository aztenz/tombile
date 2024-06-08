package com.n2o.tombile.auth.service;

import com.n2o.tombile.auth.model.entity.User;
import com.n2o.tombile.auth.model.enums.VerificationStatus;

public class RoleStrategyUser implements RoleStrategy {
    private static final String EMAIL_VERIFIED = "email verified";

    @Override
    public String handleAfterVerifyEmail(User user) {
        user.getUserData().setVerificationStatus(VerificationStatus.APPROVED);
        return EMAIL_VERIFIED;
    }
}
