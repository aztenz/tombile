package com.n2o.tombile.auth.dto;

import com.n2o.tombile.auth.model.enums.Role;
import com.n2o.tombile.validate.EnumValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RQRegister {
    private static final String USERNAME_IS_MANDATORY = "username is mandatory";
    private static final String PASSWORD_IS_MANDATORY = "password is mandatory";
    private static final String FIRST_NAME_IS_MANDATORY = "first name is mandatory";
    private static final String LAST_NAME_IS_MANDATORY = "last name is mandatory";
    private static final String ROLE_IS_MANDATORY = "role is mandatory";
    private static final String EMAIL_IS_MANDATORY = "email is mandatory";
    private static final String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String EMAIL_NOT_VALID = "this is not a valid email";
    private static final String INVALID_RULE = "invalid rule";
    @Valid

    @NotNull(message = USERNAME_IS_MANDATORY)
    @NotBlank(message = USERNAME_IS_MANDATORY)
    private String username;

    @NotNull(message = FIRST_NAME_IS_MANDATORY)
    @NotBlank(message = FIRST_NAME_IS_MANDATORY)
    private String firstName;

    @NotNull(message = LAST_NAME_IS_MANDATORY)
    @NotBlank(message = LAST_NAME_IS_MANDATORY)
    private String lastName;

    @NotNull(message = PASSWORD_IS_MANDATORY)
    @NotBlank(message = PASSWORD_IS_MANDATORY)
    private String password;

    @EnumValidator(enumClass = Role.class, allowedValues = {"USER", "SUPPLIER"}, ignoreCase = true)
    private String role;

    @NotNull(message = EMAIL_IS_MANDATORY)
    @NotBlank(message = EMAIL_IS_MANDATORY)
    @Email(regexp = EMAIL_REGEX, message = EMAIL_NOT_VALID)
    private String email;
}
