package com.n2o.tombile.dto.response.product.car;

import com.n2o.tombile.dto.response.product.abs_product.ProductDetails;
import com.n2o.tombile.model.CarState;
import lombok.Getter;

@Getter
public class CarDetails extends ProductDetails {
    private String make;
    private String model;
    private int year;
    private int mileage;
    private CarState carState;
}
