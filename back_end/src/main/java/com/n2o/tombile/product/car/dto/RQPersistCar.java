package com.n2o.tombile.product.car.dto;

import com.n2o.tombile.core.common.validate.Enum;
import com.n2o.tombile.product.car.model.CarState;
import com.n2o.tombile.product.product.dto.PersistProductRQ;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.CAR_YEAR_MAX;
import static com.n2o.tombile.core.common.util.Constants.CAR_YEAR_MIN;
import static com.n2o.tombile.core.common.util.Constants.ERROR_MAKE_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_MILEAGE_NEGATIVE;
import static com.n2o.tombile.core.common.util.Constants.ERROR_MODEL_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_YEAR_TOO_NEW;
import static com.n2o.tombile.core.common.util.Constants.ERROR_YEAR_TOO_OLD;
import static com.n2o.tombile.core.common.util.Constants.POSITIVE_NUM_MIN;

@Getter
public class RQPersistCar extends PersistProductRQ {
    @NotEmpty(message = ERROR_MAKE_REQUIRED)
    private String make;

    @NotEmpty(message = ERROR_MODEL_REQUIRED)
    private String model;

    @Min(value = CAR_YEAR_MIN, message = ERROR_YEAR_TOO_OLD)
    @Max(value = CAR_YEAR_MAX, message = ERROR_YEAR_TOO_NEW)
    private int year;

    @Min(value = POSITIVE_NUM_MIN, message = ERROR_MILEAGE_NEGATIVE)
    private int mileage;

    @Enum(enumClass = CarState.class)
    private String carState;
}
