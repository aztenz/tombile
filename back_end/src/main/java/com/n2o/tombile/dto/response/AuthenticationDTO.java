package com.n2o.tombile.dto.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationDTO {
    private static final String TOKEN_IS_MANDATORY = "token is mandatory";
    @Valid

    @NotNull(message = TOKEN_IS_MANDATORY)
    @NotBlank(message = TOKEN_IS_MANDATORY)
    private String token;
}