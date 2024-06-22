package com.n2o.tombile.core.user.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_CHARGE_NOT_NEGATIVE;
import static com.n2o.tombile.core.common.util.Constants.POSITIVE_NON_ZERO_NUM_MIN;

@Getter
public class RQCharge {
    @Min(value = POSITIVE_NON_ZERO_NUM_MIN, message = ERROR_CHARGE_NOT_NEGATIVE)
    private double charge;
}
