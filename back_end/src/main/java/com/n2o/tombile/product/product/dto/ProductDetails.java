package com.n2o.tombile.product.product.dto;

import lombok.Getter;

@Getter
public abstract class ProductDetails {
    private int id;
    private int supplierId;
    private String name;
    private String description;
    private double price;
}
