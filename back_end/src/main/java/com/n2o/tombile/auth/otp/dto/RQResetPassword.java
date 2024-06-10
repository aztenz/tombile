package com.n2o.tombile.auth.otp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RQResetPassword extends RQVerifyOtp {
    private static final int minPasswordLength = 6;
    private static final String PASSWORD_LENGTH = "password cannot be less than " +minPasswordLength+ " character";
    private static final String PASSWORD_IS_MANDATORY = "password is mandatory";
    @Valid

    @NotNull(message = PASSWORD_IS_MANDATORY)
    @NotEmpty(message = PASSWORD_IS_MANDATORY)
    @Size(min = minPasswordLength, message = PASSWORD_LENGTH)
    private String newPassword;
}
