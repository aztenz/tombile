package com.n2o.tombile.product.order.model;

import com.n2o.tombile.address.model.Address;
import com.n2o.tombile.core.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
public class Order {
    private static final String ADDRESS_ID = "shipping_address_id";
    private static final String USER_ID = "user_id";
    private static final String PAYMENT_METHOD = "payment_method";
    private static final String ORDER_STATUS = "order_status";
    private static final String PAYMENT_STATUS = "payment_status";
    private static final String AMOUNT = "total_amount";
    private static final String ORDER_DATE = "order_date";
    private static final String ORDER = "order";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = ORDER_DATE)
    private Date orderDate;

    @Column(name = AMOUNT)
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = PAYMENT_STATUS)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = ORDER_STATUS)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = PAYMENT_METHOD)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = USER_ID)
    private User user;

    @ManyToOne
    @JoinColumn(name = ADDRESS_ID)
    private Address address;

    @OneToMany(mappedBy = ORDER, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
}
