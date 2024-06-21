package com.n2o.tombile.product.car.dto;

import com.n2o.tombile.product.product.dto.RSPPersistProduct;
import com.n2o.tombile.product.car.model.CarState;
import lombok.Getter;

@Getter
public class RSPPersistCar extends RSPPersistProduct {
    private CarState carState;
    private String make;
    private String model;
    private int mileage;
    private int year;
}
