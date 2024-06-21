package com.n2o.tombile.auth.otp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_EMAIL_REQUIRED;

@Getter
public class RQSendOtp {
    @NotEmpty(message = ERROR_EMAIL_REQUIRED)
    private String email;
}
