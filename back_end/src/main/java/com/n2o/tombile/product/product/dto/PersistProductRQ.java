package com.n2o.tombile.product.product.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PersistProductRQ {

    private static final String PRICE_NOT_NEGATIVE = "price can't be negative";
    protected static final int MIN_POSITIVE = 0;
    @Valid

    private String description;

    @Min(value = MIN_POSITIVE, message = PRICE_NOT_NEGATIVE)
    private double price;
}
