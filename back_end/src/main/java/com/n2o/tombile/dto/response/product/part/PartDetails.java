package com.n2o.tombile.dto.response.product.part;

import com.n2o.tombile.dto.response.product.abs_product.ProductDetails;
import lombok.Getter;

@Getter
public class PartDetails extends ProductDetails {
    private String manufacturer;
    private String compatibility;
}
