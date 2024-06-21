package com.n2o.tombile.product.part.model;

import com.n2o.tombile.product.product.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parts", schema = "tombile")
public class CarPart extends Product {
    @Size(max = 100)
    @Column(name = "manufacturer", length = 100)
    private String manufacturer;

    @Size(max = 100)
    @Column(name = "compatibility", length = 100)
    private String compatibility;
}