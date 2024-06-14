package com.n2o.tombile.address.dto;

import com.n2o.tombile.address.model.AddressType;
import com.n2o.tombile.core.common.validate.Enum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_CITY_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_COUNTRY_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_STREET_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_ZIP_CODE_REQUIRED;

@Getter
public class RQAddAddress {
    @NotNull(message = ERROR_STREET_REQUIRED)
    @NotEmpty(message = ERROR_STREET_REQUIRED)
    String street;

    @NotNull(message = ERROR_CITY_REQUIRED)
    @NotEmpty(message = ERROR_CITY_REQUIRED)
    String city;

    @NotNull(message = ERROR_ZIP_CODE_REQUIRED)
    @NotEmpty(message = ERROR_ZIP_CODE_REQUIRED)
    String zipCode;

    @NotNull(message = ERROR_COUNTRY_REQUIRED)
    @NotEmpty(message = ERROR_COUNTRY_REQUIRED)
    String country;

    @Enum(enumClass = AddressType.class, ignoreCase = true)
    String addressType;
}
