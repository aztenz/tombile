package com.n2o.tombile.product.order.repository;

import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.product.order.model.order_item.OrderHistoryItem;
import com.n2o.tombile.product.order.model.order_item.OrderItemHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemHistoryRepository extends JpaRepository<OrderHistoryItem, OrderItemHistoryId> {
    List<OrderHistoryItem> findAllByOrderId(int orderId);

    @Query("FROM OrderHistoryItem oih " +
            "JOIN OrderHistory oh ON oih.order = oh " +
            "JOIN Product p ON oih.product = p " +
            "WHERE oh.id = :orderId " +
            "AND p.supplier = :supplier")
    List<OrderHistoryItem> findAllByOrderAndSupplier(int orderId, User supplier);
}
