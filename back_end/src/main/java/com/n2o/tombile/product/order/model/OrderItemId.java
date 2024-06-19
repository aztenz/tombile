package com.n2o.tombile.product.order.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class OrderItemId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -2890605446908782392L;

    @NotNull
    @Column(name = "order_id", nullable = false)
    private int orderId;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private int productId;
}