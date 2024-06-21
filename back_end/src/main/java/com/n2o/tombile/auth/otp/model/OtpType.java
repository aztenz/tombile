package com.n2o.tombile.auth.otp.model;

import com.n2o.tombile.core.common.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OtpType {
    VERIFY_EMAIL(Constants.VERIFY_EMAIL_SUBJECT, Constants.VERIFY_EMAIL_BODY),
    RECOVER_PASSWORD(Constants.RECOVER_PASSWORD_SUBJECT, Constants.RECOVER_PASSWORD_BODY);

    private final String subject;
    private final String body;
}