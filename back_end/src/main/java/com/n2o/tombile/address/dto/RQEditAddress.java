package com.n2o.tombile.address.dto;

import com.n2o.tombile.address.model.AddressType;
import com.n2o.tombile.core.common.validate.annotation.Enum;
import com.n2o.tombile.core.common.validate.annotation.NotEmptyIfPresent;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_CITY_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_COUNTRY_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_STREET_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_ZIP_CODE_REQUIRED;

@Getter
public class RQEditAddress {
    @NotEmptyIfPresent(message = ERROR_CITY_REQUIRED)
    String street;

    @NotEmptyIfPresent(message = ERROR_COUNTRY_REQUIRED)
    String city;

    @NotEmptyIfPresent(message = ERROR_STREET_REQUIRED)
    String zipCode;

    @NotEmptyIfPresent(message = ERROR_ZIP_CODE_REQUIRED)
    String country;

    @Enum(enumClass = AddressType.class, ignoreCase = true)
    String addressType;
}
