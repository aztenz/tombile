package com.n2o.tombile.product.part.dto;

import com.n2o.tombile.product.product.dto.RQPersistProduct;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_COMPATIBILITY_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_MANUFACTURER_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_QUANTITY_NEGATIVE;
import static com.n2o.tombile.core.common.util.Constants.POSITIVE_NON_ZERO_NUM_MIN;

@Getter
public class RQPersistPart extends RQPersistProduct {
    @NotEmpty(message = ERROR_MANUFACTURER_REQUIRED)
    private String manufacturer;

    @NotEmpty(message = ERROR_COMPATIBILITY_REQUIRED)
    private String compatibility;

    @Min(value = POSITIVE_NON_ZERO_NUM_MIN, message = ERROR_QUANTITY_NEGATIVE)
    private int quantity;
}
