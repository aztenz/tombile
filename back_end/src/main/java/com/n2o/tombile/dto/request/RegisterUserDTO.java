package com.n2o.tombile.dto.request;

import com.n2o.tombile.enums.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO {
    private static final String USERNAME_IS_MANDATORY = "username is mandatory";
    private static final String PASSWORD_IS_MANDATORY = "password is mandatory";
    private static final String ROLE_IS_MANDATORY = "role is mandatory";
    private static final String EMAIL_IS_MANDATORY = "email is mandatory";
    private static final String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String EMAIL_NOT_VALID = "this is not a valid email";
    @Valid

    @NotNull(message = USERNAME_IS_MANDATORY)
    @NotBlank(message = USERNAME_IS_MANDATORY)
    private String username;

    @NotNull(message = PASSWORD_IS_MANDATORY)
    @NotBlank(message = PASSWORD_IS_MANDATORY)
    private String password;

    @NotNull(message = ROLE_IS_MANDATORY)
    @NotBlank(message = ROLE_IS_MANDATORY)
    private Role role;

    @NotNull(message = EMAIL_IS_MANDATORY)
    @NotBlank(message = EMAIL_IS_MANDATORY)
    @Email(regexp = EMAIL_REGEX, message = EMAIL_NOT_VALID)
    private String email;
}
