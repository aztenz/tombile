package com.n2o.tombile.product.part.dto;

import com.n2o.tombile.product.product.dto.ProductDetails;
import lombok.Getter;

@Getter
public class PartDetails extends ProductDetails {
    private String manufacturer;
    private String compatibility;
}
