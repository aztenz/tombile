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

    public static final String ORDER_ID = "order_id";
    public static final String PRODUCT_ID = "product_id";
    public static final String SUBTOTAL = "subtotal";
    public static final String PRICE = "price";
    public static final String QUANTITY = "quantity";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = QUANTITY)
    private int quantity;

    @Column(name = PRICE)
    private double price;

    @Column(name = SUBTOTAL, insertable = false, updatable = false, nullable = false)
    private double subtotal;

    @ManyToOne
    @JoinColumn(name = PRODUCT_ID)
    private Product product;

    @ManyToOne
    @JoinColumn(name = ORDER_ID)
    private Order order;
}