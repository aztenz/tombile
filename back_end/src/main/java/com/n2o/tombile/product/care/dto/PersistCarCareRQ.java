package com.n2o.tombile.product.care.dto;

import com.n2o.tombile.core.common.validate.Enum;
import com.n2o.tombile.product.care.model.CarCareType;
import com.n2o.tombile.product.product.dto.PersistProductRQ;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_CONTACT_INFO_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_LOCATION_REQUIRED;

@Getter
public class PersistCarCareRQ extends PersistProductRQ {
    @NotEmpty(message = ERROR_CONTACT_INFO_REQUIRED)
    private String contactInfo;

    @NotEmpty(message = ERROR_LOCATION_REQUIRED)
    private String location;

    @Enum(enumClass = CarCareType.class)
    private String carCareType;
}
