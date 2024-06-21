package com.n2o.tombile.product.order.model.order_item;

import com.n2o.tombile.product.order.model.order.OrderHistory;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_items_history", schema = "tombile")
public class OrderHistoryItem extends OrderItemModel {
    @EmbeddedId
    private OrderItemHistoryId id;

    @MapsId("orderId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderHistory order;
}