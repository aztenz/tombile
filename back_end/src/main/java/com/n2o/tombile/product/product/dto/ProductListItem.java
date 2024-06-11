package com.n2o.tombile.product.product.dto;

import lombok.Getter;

@Getter
public abstract class ProductListItem {
    private int id;
    private String name;
    private double price;
}
