package com.n2o.tombile.model;

import com.n2o.tombile.enums.CarState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Getter @Setter
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
