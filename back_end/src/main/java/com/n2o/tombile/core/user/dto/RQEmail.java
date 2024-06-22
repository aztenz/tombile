package com.n2o.tombile.core.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.EMAIL_REGEX;
import static com.n2o.tombile.core.common.util.Constants.ERROR_EMAIL_INVALID;
import static com.n2o.tombile.core.common.util.Constants.ERROR_EMAIL_REQUIRED;

@Getter
public class RQEmail {
    @NotEmpty(message = ERROR_EMAIL_REQUIRED)
    @Email(regexp = EMAIL_REGEX, message = ERROR_EMAIL_INVALID)
    private String email;
}
