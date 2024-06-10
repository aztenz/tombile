package com.n2o.tombile.core.user.service;

import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.core.user.model.VerificationStatus;

public class RoleStrategyUser implements RoleStrategy {
    private static final String EMAIL_VERIFIED = "email verified";

    @Override
    public String handleAfterVerifyEmail(User user) {
        user.getUserData().setVerificationStatus(VerificationStatus.APPROVED);
        return EMAIL_VERIFIED;
    }
}
