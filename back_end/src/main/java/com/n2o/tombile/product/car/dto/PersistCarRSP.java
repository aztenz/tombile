package com.n2o.tombile.product.car.dto;

import com.n2o.tombile.product.product.dto.PersistProductRSP;
import com.n2o.tombile.product.car.model.CarState;
import lombok.Getter;

@Getter
public class PersistCarRSP extends PersistProductRSP {
    private CarState carState;
    private String make;
    private String model;
    private int mileage;
    private int year;
}
