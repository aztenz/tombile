package com.n2o.tombile.auth.otp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_EMAIL_REQUIRED;

@Getter
public class RQSendOtp {
    @NotNull(message = ERROR_EMAIL_REQUIRED)
    @NotBlank(message = ERROR_EMAIL_REQUIRED)
    private String email;
}
