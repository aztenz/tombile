package com.n2o.tombile.product.order.model.order;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "orders", schema = "tombile")
public class Order extends OrderModel {
}