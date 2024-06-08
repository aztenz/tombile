package com.n2o.tombile.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RQVerifyEmail {
    public static final String EMAIL_IS_MANDATORY = "email is mandatory";

    @NotNull(message = EMAIL_IS_MANDATORY)
    @NotBlank(message = EMAIL_IS_MANDATORY)
    private String email;

    private int otp;
}
