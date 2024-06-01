package com.n2o.tombile.dto.request.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PersistPartRQ extends PersistProductRQ {
    private static final String MANUFACTURER_IS_MANDATORY = "manufacturer is mandatory";
    private static final String COMPATIBILITY_IS_MANDATORY = "compatibility is mandatory";
    @Valid

    @NotNull(message = MANUFACTURER_IS_MANDATORY)
    @NotEmpty(message = MANUFACTURER_IS_MANDATORY)
    private String manufacturer;

    @NotNull(message = COMPATIBILITY_IS_MANDATORY)
    @NotEmpty(message = COMPATIBILITY_IS_MANDATORY)
    private String compatibility;
}
