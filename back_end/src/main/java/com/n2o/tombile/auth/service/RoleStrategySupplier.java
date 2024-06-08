package com.n2o.tombile.auth.service;

import com.n2o.tombile.auth.model.entity.User;
import com.n2o.tombile.auth.model.enums.VerificationStatus;

public class RoleStrategySupplier implements RoleStrategy {
    private static final String SENT_TO_ADMIN = "email sent to an admin for approval";

    @Override
    public String handleAfterVerifyEmail(User user) {
        user.getUserData().setVerificationStatus(VerificationStatus.VERIFIED);
        return SENT_TO_ADMIN;
    }
}
