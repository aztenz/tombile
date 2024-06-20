package com.n2o.tombile.product.order.model;

import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.product.product.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "carts", schema = "tombile")
public class Cart {
    @EmbeddedId
    private CartId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "subtotal", insertable = false, updatable = false)
    private double subtotal;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "added_date", insertable = false, updatable = false)
    private Instant addedDate;
}