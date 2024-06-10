package com.n2o.tombile.auth.otp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OtpType {
    VERIFY_EMAIL(Constants.VERIFY_EMAIL_SUBJECT, Constants.VERIFY_EMAIL_BODY),
    RECOVER_PASSWORD(Constants.RECOVER_PASSWORD_SUBJECT, Constants.RECOVER_PASSWORD_BODY);

    private final String subject;
    private final String body;

    private static class Constants {
        public static final String VERIFY_EMAIL_SUBJECT = "Your account verification code";

        public static final String VERIFY_EMAIL_BODY =
                """
                Dear {0},
                Thank you for registering with Tombile.
                To complete your registration, please verify your email address.
                Please use the following One-Time Password (OTP) to verify your email address: {1}
                If you did not create an account, please ignore this email.
                """;

        public static final String RECOVER_PASSWORD_SUBJECT = "Your Password Recovery OTP";
        public static final String RECOVER_PASSWORD_BODY =
                """
                Dear {0},
                We received a request to reset your password for your tombile account.
                If you did not request a password reset, please ignore this email.
                To reset your password, please use the following One-Time Password (OTP): {1}
                """;
    }
}