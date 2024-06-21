package com.n2o.tombile.product.order.model.order_item;

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
public class OrderItemHistoryId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 7273078305917831117L;

    @NotNull
    @Column(name = "order_id", nullable = false)
    private int orderId;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private int productId;
}