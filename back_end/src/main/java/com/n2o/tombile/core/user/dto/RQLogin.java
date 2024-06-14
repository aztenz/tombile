package com.n2o.tombile.core.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_PASSWORD_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_USERNAME_REQUIRED;

@Getter
public class RQLogin {
    @NotEmpty(message = ERROR_USERNAME_REQUIRED)
    private String username;

    @NotEmpty(message = ERROR_PASSWORD_REQUIRED)
    private String password;
}