package com.n2o.tombile.core.user.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RQLogin {
    private static final String USERNAME_IS_MANDATORY = "username is mandatory";
    private static final String PASSWORD_IS_MANDATORY = "password is mandatory";
    @Valid

    @NotNull(message = USERNAME_IS_MANDATORY)
    @NotBlank(message = USERNAME_IS_MANDATORY)
    private String username;

    @NotNull(message = PASSWORD_IS_MANDATORY)
    @NotBlank(message = PASSWORD_IS_MANDATORY)
    private String password;
}