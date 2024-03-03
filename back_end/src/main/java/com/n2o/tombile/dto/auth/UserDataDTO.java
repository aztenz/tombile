package com.n2o.tombile.dto.auth;

import com.n2o.tombile.enums.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDTO {
    public static final String ROLE_IS_MANDATORY = "role is mandatory";
    public static final String EMAIL_IS_MANDATORY = "email is mandatory";
    public static final String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String EMAIL_NOT_VALID = "this is not a valid email";

    @Valid
    @NotNull(message = ROLE_IS_MANDATORY)
    private Role role;

    @NotNull(message = EMAIL_IS_MANDATORY)
    @NotBlank(message = EMAIL_IS_MANDATORY)
    @Email(regexp = EMAIL_REGEX, message = EMAIL_NOT_VALID)
    private String email;
}
