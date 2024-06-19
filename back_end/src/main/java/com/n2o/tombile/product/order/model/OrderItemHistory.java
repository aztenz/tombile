package com.n2o.tombile.product.order.model;

import com.n2o.tombile.product.product.model.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "order_items_history", schema = "tombile")
public class OrderItemHistory {
    @EmbeddedId
    private OrderItemHistoryId id;

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderHistory order;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'FINISHED'")
    @Column(name = "item_status")
    private OrderStatus itemStatus;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "subtotal")
    private double subtotal;
}