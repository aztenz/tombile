package com.n2o.tombile.dto.response.product.abs_product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PersistProductRSP {
    private int id;
    private String supplierName;
    private String supplierEmail;
    private String name;
    private String description;
    private double price;
}
