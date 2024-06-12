package com.n2o.tombile.product.product.model;

import com.n2o.tombile.core.user.model.User;
import jakarta.persistence.*;
import lombok.Setter;

@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "products")
public class Product {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String DESCRIPTION = "description";
    private static final String SUPPLIER_ID = "supplier_id";
    private static final String PRODUCT_TYPE = "product_type";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private int id;

    @Column(name = NAME)
    private String name;

    @Column(name = DESCRIPTION)
    private String description;

    @Column(name = PRICE)
    private double price;

    @ManyToOne
    @JoinColumn(name = SUPPLIER_ID)
    private User supplier;

    @Enumerated(EnumType.STRING)
    @Column(name = PRODUCT_TYPE)
    private ProductType productType;
}