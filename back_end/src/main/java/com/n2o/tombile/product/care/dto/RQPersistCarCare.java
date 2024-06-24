package com.n2o.tombile.product.care.dto;

import com.n2o.tombile.core.common.validate.annotation.Enum;
import com.n2o.tombile.product.care.model.CarCareType;
import com.n2o.tombile.product.product.dto.RQPersistProduct;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_CAR_CARE_TYPE_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_CONTACT_INFO_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_LOCATION_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_NAME_REQUIRED;

@Setter
@Getter
public class RQPersistCarCare extends RQPersistProduct {
    @NotEmpty(message = ERROR_CONTACT_INFO_REQUIRED)
    private String contactInfo;

    @NotEmpty(message = ERROR_LOCATION_REQUIRED)
    private String location;

    @NotEmpty(message = ERROR_NAME_REQUIRED)
    private String name;

    @NotEmpty(message = ERROR_CAR_CARE_TYPE_REQUIRED)
    @Enum(enumClass = CarCareType.class)
    private String serviceType;
}
