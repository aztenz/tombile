package com.n2o.tombile.dto.response.car;

import com.n2o.tombile.enums.CarState;
import com.n2o.tombile.enums.ProductType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CarDetails {
    private int id;
    private int supplierId;
    private String name;
    private String description;
    private double price;

    private String make;
    private String model;
    private int year;
    private int mileage;
    private CarState carState;
}
