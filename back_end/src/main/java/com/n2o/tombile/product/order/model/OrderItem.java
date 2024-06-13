package com.n2o.tombile.product.order.model;

import com.n2o.tombile.product.product.model.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "order_id")
    private int quantity;

    @Column(name = "price")
    private double price;

    @Column(name = "subtotal", insertable = false, updatable = false, nullable = false)
    private double subtotal;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}