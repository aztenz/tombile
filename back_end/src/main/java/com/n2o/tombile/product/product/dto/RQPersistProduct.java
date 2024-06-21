package com.n2o.tombile.product.product.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_PRICE_NEGATIVE;
import static com.n2o.tombile.core.common.util.Constants.ERROR_QUANTITY_NEGATIVE;
import static com.n2o.tombile.core.common.util.Constants.POSITIVE_NON_ZERO_NUM_MIN;
import static com.n2o.tombile.core.common.util.Constants.POSITIVE_NUM_MIN;

@Getter
@Setter
public abstract class RQPersistProduct {
    private String description;

    @Min(value = POSITIVE_NUM_MIN, message = ERROR_PRICE_NEGATIVE)
    private double price;

    @Min(value = POSITIVE_NON_ZERO_NUM_MIN, message = ERROR_QUANTITY_NEGATIVE)
    private int quantity;
}
