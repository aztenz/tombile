package com.n2o.tombile.product.car.dto;

import com.n2o.tombile.product.car.model.CarState;
import com.n2o.tombile.product.product.dto.ProductDetails;
import lombok.Getter;

@Getter
public class CarDetails extends ProductDetails {
    private String make;
    private String model;
    private int year;
    private int mileage;
    private CarState carState;
}
