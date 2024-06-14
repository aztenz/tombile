package com.n2o.tombile.auth.otp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_PASSWORD_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_PASSWORD_TOO_SHORT;
import static com.n2o.tombile.core.common.util.Constants.PASSWORD_MIN_LENGTH;

@Getter
public class RQResetPassword extends RQVerifyOtp {
    @NotNull(message = ERROR_PASSWORD_REQUIRED)
    @NotEmpty(message = ERROR_PASSWORD_REQUIRED)
    @Size(min = PASSWORD_MIN_LENGTH, message = ERROR_PASSWORD_TOO_SHORT)
    private String newPassword;
}
