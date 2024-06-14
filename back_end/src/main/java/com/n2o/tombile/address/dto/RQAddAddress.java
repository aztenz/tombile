package com.n2o.tombile.address.dto;

import com.n2o.tombile.address.model.AddressType;
import com.n2o.tombile.core.common.validate.Enum;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_ADDRESS_TYPE_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_CITY_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_COUNTRY_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_STREET_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_ZIP_CODE_REQUIRED;

@Getter
public class RQAddAddress {
    @NotEmpty(message = ERROR_STREET_REQUIRED)
    String street;

    @NotEmpty(message = ERROR_CITY_REQUIRED)
    String city;

    @NotEmpty(message = ERROR_ZIP_CODE_REQUIRED)
    String zipCode;

    @NotEmpty(message = ERROR_COUNTRY_REQUIRED)
    String country;

    @NotEmpty(message = ERROR_ADDRESS_TYPE_REQUIRED)
    @Enum(enumClass = AddressType.class, ignoreCase = true)
    String addressType;
}
