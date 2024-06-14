package com.n2o.tombile.product.part.dto;

import com.n2o.tombile.product.product.dto.PersistProductRQ;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import static com.n2o.tombile.core.common.util.Constants.ERROR_COMPATIBILITY_REQUIRED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_MANUFACTURER_REQUIRED;

@Getter
public class PersistPartRQ extends PersistProductRQ {
    @NotEmpty(message = ERROR_MANUFACTURER_REQUIRED)
    private String manufacturer;

    @NotEmpty(message = ERROR_COMPATIBILITY_REQUIRED)
    private String compatibility;
}
