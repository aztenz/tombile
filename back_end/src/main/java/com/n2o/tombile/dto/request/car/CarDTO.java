package com.n2o.tombile.dto.request.car;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    public static final String NAME_IS_MANDATORY = "name is mandatory";
    public static final String PRICE_NOT_NEGATIVE = "price can't be negative";
    public static final String MAKE_IS_MANDATORY = "make is mandatory";
    public static final String MODEL_IS_MANDATORY = "model is mandatory";
    public static final String CAR_STATE_IS_MANDATORY = "car state is mandatory";
    @Valid

    @NotNull(message = NAME_IS_MANDATORY)
    @NotBlank(message = NAME_IS_MANDATORY)
    private String name;

    private String description;

    @Min(value = 0, message = PRICE_NOT_NEGATIVE)
    private double price;

    @NotNull(message = MAKE_IS_MANDATORY)
    @NotBlank(message = MAKE_IS_MANDATORY)
    private String make;

    @NotNull(message = MODEL_IS_MANDATORY)
    @NotBlank(message = MODEL_IS_MANDATORY)
    private String model;


    private int year;
    private int mileage;

    @NotNull(message = CAR_STATE_IS_MANDATORY)
    @NotBlank(message = CAR_STATE_IS_MANDATORY)
    private String carState;
}