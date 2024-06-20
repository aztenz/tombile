package com.n2o.tombile.product.order.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_QUANTITY_NEGATIVE;
import static com.n2o.tombile.core.common.util.Constants.POSITIVE_NON_ZERO_NUM_MIN;

@Getter
public class RQCart {
    private int productId;

    @Min(value = POSITIVE_NON_ZERO_NUM_MIN, message = ERROR_QUANTITY_NEGATIVE)
    private int quantity;
}
