package com.n2o.tombile.product.product.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class RSPProductDetails {
    private int id;
    private String supplierName;
    private String supplierEmail;
    private String name;
    private String description;
    private double price;
}
