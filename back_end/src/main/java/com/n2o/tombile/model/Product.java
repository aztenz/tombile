package com.n2o.tombile.model;

import com.n2o.tombile.enums.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "products")
public abstract class Product {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String DESCRIPTION = "description";
    private static final String SUPPLIER_ID = "supplier_id";
    private static final String PRODUCT_TYPE = "product_type";
    private static final String PRODUCT_DETAILS_ID = "product_details_id";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private int id;

    @Column(name = SUPPLIER_ID)
    private int supplierId;

    @Column(name = NAME)
    private String name;

    @Column(name = DESCRIPTION)
    private String description;

    @Column(name = PRICE)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = PRODUCT_TYPE)
    private ProductType productType;
}