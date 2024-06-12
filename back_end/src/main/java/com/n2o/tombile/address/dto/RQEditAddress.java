package com.n2o.tombile.address.dto;

import com.n2o.tombile.address.model.AddressType;
import com.n2o.tombile.core.common.validate.Enum;
import com.n2o.tombile.core.common.validate.NotEmptyIfPresent;
import lombok.Getter;

@Getter
public class RQEditAddress {
    private static final String STREET_IS_MANDATORY = "street can't be empty";
    private static final String CITY_IS_MANDATORY = "city can't be empty";
    private static final String ZIP_CODE_IS_MANDATORY = "zip code can't be empty";
    private static final String COUNTRY_IS_MANDATORY = "country can't be empty";

    @NotEmptyIfPresent(message = STREET_IS_MANDATORY)
    String street;

    @NotEmptyIfPresent(message = CITY_IS_MANDATORY)
    String city;

    @NotEmptyIfPresent(message = ZIP_CODE_IS_MANDATORY)
    String zipCode;

    @NotEmptyIfPresent(message = COUNTRY_IS_MANDATORY)
    String country;

    @Enum(enumClass = AddressType.class, ignoreCase = true)
    String addressType;
}
