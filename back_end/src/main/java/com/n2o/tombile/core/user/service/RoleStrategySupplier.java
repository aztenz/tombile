package com.n2o.tombile.core.user.service;

import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.core.user.model.VerificationStatus;

import static com.n2o.tombile.core.common.util.Constants.EMAIL_SENT_TO_ADMIN;

public class RoleStrategySupplier implements RoleStrategy {

    @Override
    public String handleAfterVerifyEmail(User user) {
        user.getUserData().setVerificationStatus(VerificationStatus.VERIFIED);
        return EMAIL_SENT_TO_ADMIN;
    }
}
