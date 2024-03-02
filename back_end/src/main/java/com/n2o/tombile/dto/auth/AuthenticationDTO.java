package com.n2o.tombile.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationDTO {
    private String token;
    public AuthenticationDTO(String token) {
        this.token = token;
    }

}
