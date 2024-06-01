package com.n2o.tombile.dto.response.product.abs_product;

import lombok.Getter;

@Getter
public abstract class ProductListItem {
    private int id;
    private String name;
    private double price;
}
