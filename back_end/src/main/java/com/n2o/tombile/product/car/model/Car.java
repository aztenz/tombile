package com.n2o.tombile.product.car.model;

import com.n2o.tombile.product.product.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "cars")
public class Car extends Product {
    private static final String MAKE = "make";
    private static final String MODEL = "model";
    private static final String YEAR = "year";
    private static final String MILEAGE = "mileage";
    private static final String CAR_STATE = "car_state";

    @Column(name = MAKE)
    private String make;

    @Column(name = MODEL)
    private String model;

    @Column(name = YEAR)
    private int year;

    @Column(name = MILEAGE)
    private int mileage;

    @Enumerated(EnumType.STRING)
    @Column(name = CAR_STATE)
    private CarState carState;
}
