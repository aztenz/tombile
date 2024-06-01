package com.n2o.tombile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "parts")
public class CarPart extends Product {
    private static final String COMPATIBILITY = "compatibility";
    private static final String MANUFACTURER = "manufacturer";

    @Column(name = MANUFACTURER)
    private String manufacturer;

    @Column(name = COMPATIBILITY)
    private String compatibility;
}
