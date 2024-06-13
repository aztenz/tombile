package com.n2o.tombile.product.part.model;

import com.n2o.tombile.product.product.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "parts")
public class CarPart extends Product {
    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "compatibility")
    private String compatibility;
}
