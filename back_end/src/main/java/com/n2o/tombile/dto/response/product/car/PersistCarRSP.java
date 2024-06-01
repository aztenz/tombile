package com.n2o.tombile.dto.response.product.car;

import com.n2o.tombile.dto.response.product.abs_product.PersistProductRSP;
import com.n2o.tombile.enums.CarState;
import lombok.Getter;

@Getter
public class PersistCarRSP extends PersistProductRSP {
    private CarState carState;
    private String make;
    private String model;
    private int mileage;
    private int year;
}
