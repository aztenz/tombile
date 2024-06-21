package com.n2o.tombile.product.order.model.cart;

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
public class CartId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 4674072741081418471L;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private int userId;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private int productId;
}