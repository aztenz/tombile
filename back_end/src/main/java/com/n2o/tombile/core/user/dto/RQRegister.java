package com.n2o.tombile.core.user.dto;

import com.n2o.tombile.core.common.validate.annotation.Enum;
import com.n2o.tombile.core.user.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.EMAIL_REGEX;
import static com.n2o.tombile.core.common.util.Constants.ERROR_EMAIL_INVALID;
import static com.n2o.tombile.core.common.util.Constants.ERROR_EMAIL_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_FIRST_NAME_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_LAST_NAME_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_PASSWORD_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_ROLE_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_USERNAME_REQUIRED;

@Getter
public class RQRegister {
    @NotEmpty(message = ERROR_USERNAME_REQUIRED)
    private String username;
    
    @NotEmpty(message = ERROR_FIRST_NAME_REQUIRED)
    private String firstName;
    
    @NotEmpty(message = ERROR_LAST_NAME_REQUIRED)
    private String lastName;
    
    @NotEmpty(message = ERROR_PASSWORD_REQUIRED)
    private String password;

    @NotEmpty(message = ERROR_ROLE_REQUIRED)
    @Enum(enumClass = Role.class, allowedValues = {"USER", "SUPPLIER"}, ignoreCase = true)
    private String role;

    @NotEmpty(message = ERROR_EMAIL_REQUIRED)
    @Email(regexp = EMAIL_REGEX, message = ERROR_EMAIL_INVALID)
    private String email;
}
