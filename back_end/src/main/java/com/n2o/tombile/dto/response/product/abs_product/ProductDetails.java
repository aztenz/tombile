package com.n2o.tombile.dto.response.product.abs_product;

import lombok.Getter;

@Getter
public abstract class ProductDetails {
    private int id;
    private int supplierId;
    private String name;
    private String description;
    private double price;
}
