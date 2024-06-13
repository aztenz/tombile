package com.n2o.tombile.product.product.model;

import com.n2o.tombile.core.user.model.User;
import jakarta.persistence.*;
import lombok.Setter;

@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private User supplier;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;
}