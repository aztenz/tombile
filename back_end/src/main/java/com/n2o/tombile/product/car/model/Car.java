package com.n2o.tombile.product.car.model;

import com.n2o.tombile.product.product.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cars", schema = "tombile")
public class Car extends Product {
    @Size(max = 50)
    @Column(name = "make", length = 50)
    private String make;

    @Size(max = 50)
    @Column(name = "model", length = 50)
    private String model;

    @Column(name = "year")
    private int year;

    @Column(name = "mileage")
    private int mileage;

    @Size(max = 20)
    @Enumerated(EnumType.STRING)
    @Column(name = "car_state", length = 20)
    private CarState carState;
}