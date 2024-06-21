package com.n2o.tombile.product.order.repository;

import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.product.order.model.order.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Integer> {
    List<OrderHistory> findAllByBuyer(User buyer);

    @Query("FROM OrderHistory oh " +
            "JOIN OrderHistoryItem oih ON oih.order = oh " +
            "JOIN Product p ON oih.product = p " +
            "WHERE p.supplier = :supplier")
    List<OrderHistory> findAllBySupplier(User supplier);

    Optional<OrderHistory> findByIdAndBuyer(int oId, User buyer);

    @Query("FROM OrderHistory oh " +
            "JOIN OrderHistoryItem oih on oih.order = oh " +
            "JOIN Product p ON oih.product = p " +
            "WHERE oh.id = :oId " +
            "AND p.supplier = :supplier")
    Optional<OrderHistory> findByIdAndSupplier(int oId, User supplier);
}
