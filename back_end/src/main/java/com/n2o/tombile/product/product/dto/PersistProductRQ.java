package com.n2o.tombile.product.product.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_PRICE_NEGATIVE;
import static com.n2o.tombile.core.common.util.Constants.POSITIVE_NUM_MIN;

@Getter
@Setter
public abstract class PersistProductRQ {
    private String description;

    @Min(value = POSITIVE_NUM_MIN, message = ERROR_PRICE_NEGATIVE)
    private double price;
}
