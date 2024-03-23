package com.n2o.tombile.dto.response.car;

import com.n2o.tombile.enums.CarState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostCarRSP {
    private int id;
    private int supplierId;
    private String name;
    private String description;
    private double price;

    private CarState carState;
    private String make;
    private String model;
    private int mileage;
    private int year;
}