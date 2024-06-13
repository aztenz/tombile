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
    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private int year;

    @Column(name = "mileage")
    private int mileage;

    @Enumerated(EnumType.STRING)
    @Column(name = "car_state")
    private CarState carState;
}
