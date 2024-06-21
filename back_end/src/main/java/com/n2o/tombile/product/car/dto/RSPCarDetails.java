package com.n2o.tombile.product.car.dto;

import com.n2o.tombile.product.car.model.CarState;
import com.n2o.tombile.product.product.dto.RSPProductDetails;
import lombok.Getter;

@Getter
public class RSPCarDetails extends RSPProductDetails {
    private String make;
    private String model;
    private int year;
    private int mileage;
    private CarState carState;
}
