package com.n2o.tombile.address.dto;

import com.n2o.tombile.address.model.AddressType;
import com.n2o.tombile.core.common.validate.Enum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RQAddAddress {
    private static final String STREET_IS_MANDATORY = "street is mandatory";
    private static final String CITY_IS_MANDATORY = "city is mandatory";
    private static final String ZIP_CODE_IS_MANDATORY = "zip code is mandatory";
    private static final String COUNTRY_IS_MANDATORY = "country is mandatory";

    @NotNull(message = STREET_IS_MANDATORY)
    @NotEmpty(message = STREET_IS_MANDATORY)
    String street;

    @NotNull(message = CITY_IS_MANDATORY)
    @NotEmpty(message = CITY_IS_MANDATORY)
    String city;

    @NotNull(message = ZIP_CODE_IS_MANDATORY)
    @NotEmpty(message = ZIP_CODE_IS_MANDATORY)
    String zipCode;

    @NotNull(message = COUNTRY_IS_MANDATORY)
    @NotEmpty(message = COUNTRY_IS_MANDATORY)
    String country;

    @Enum(enumClass = AddressType.class, ignoreCase = true)
    String addressType;
}
