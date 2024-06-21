package com.n2o.tombile.product.order.repository;

import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.product.order.model.order_item.OrderItem;
import com.n2o.tombile.product.order.model.order_item.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
    List<OrderItem> findAllByOrderId(int orderId);

    @Query("FROM OrderItem oi " +
            "JOIN Order o ON oi.order = o " +
            "JOIN Product p ON oi.product = p " +
            "WHERE o.id = :orderId " +
            "AND p.supplier = :supplier")
    List<OrderItem> findAllByOrderAndSupplier(int orderId, User supplier);
}
