package com.n2o.tombile.product.product.model;

import com.n2o.tombile.core.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products", schema = "tombile")
@Inheritance(strategy = InheritanceType.JOINED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private User supplier;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;

    @Column(name = "quantity")
    private int quantity;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "image_path", length = 100)
    private String imagePath;
}