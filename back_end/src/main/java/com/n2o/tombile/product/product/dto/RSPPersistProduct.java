package com.n2o.tombile.product.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class RSPPersistProduct {
    private int id;
    private String supplierName;
    private String supplierEmail;
    private String name;
    private String description;
    private double price;
}
