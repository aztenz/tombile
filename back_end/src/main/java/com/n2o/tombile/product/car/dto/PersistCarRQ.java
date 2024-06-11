package com.n2o.tombile.product.car.dto;

import com.n2o.tombile.product.product.dto.PersistProductRQ;
import com.n2o.tombile.product.car.model.CarState;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PersistCarRQ extends PersistProductRQ {
    private static final String MAKE_IS_MANDATORY = "make is mandatory";
    private static final String MODEL_IS_MANDATORY = "model is mandatory";
    private static final String CAR_STATE_IS_MANDATORY = "car state is mandatory";
    private static final String YEAR_MUST_BE_AFTER_1950 = "year must be after 1950";
    private static final String YEAR_MUST_BE_BEFORE_2024 = "year must be before 2024";
    private static final String MILEAGE_NOT_NEGATIVE = "mileage can't be negative";
    private static final long MIN_YEAR = 1950L;
    private static final long MAX_YEAR = 2014L;
    @Valid

    @NotNull(message = MAKE_IS_MANDATORY)
    @NotBlank(message = MAKE_IS_MANDATORY)
    private String make;

    @NotNull(message = MODEL_IS_MANDATORY)
    @NotBlank(message = MODEL_IS_MANDATORY)
    private String model;


    @Min(value = MIN_YEAR, message = YEAR_MUST_BE_AFTER_1950)
    @Max(value = MAX_YEAR, message = YEAR_MUST_BE_BEFORE_2024)
    private int year;

    @Min(value = MIN_POSITIVE, message = MILEAGE_NOT_NEGATIVE)
    private int mileage;

    @NotNull(message = CAR_STATE_IS_MANDATORY)
    private CarState carState;
}
