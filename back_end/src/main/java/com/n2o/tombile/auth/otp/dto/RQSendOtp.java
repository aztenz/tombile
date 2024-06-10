package com.n2o.tombile.auth.otp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RQSendOtp {
    public static final String EMAIL_IS_MANDATORY = "email is mandatory";
    @Valid

    @NotNull(message = EMAIL_IS_MANDATORY)
    @NotBlank(message = EMAIL_IS_MANDATORY)
    private String email;
}
