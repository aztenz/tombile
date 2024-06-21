package com.n2o.tombile.auth.token.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RSPToken {
    private String token;
    private String role;
}