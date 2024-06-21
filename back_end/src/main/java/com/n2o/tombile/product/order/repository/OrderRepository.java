package com.n2o.tombile.product.order.repository;

import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.product.order.model.order.Order;
import com.n2o.tombile.product.order.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Procedure(procedureName = "create_order", outputParameterName = "p_order_id")
    int createOrder(@Param("p_buyer_id") int buyerId, @Param("p_shipping_address_id") int shippingAddressId);

    @Procedure(procedureName = "confirm_order_items")
    void confirmOrderItems(@Param("p_order_id") int orderId, @Param("p_supplier_id") int supplierId);

    @Procedure(procedureName = "reject_order_items")
    void rejectOrderItems(@Param("p_order_id") int orderId, @Param("p_supplier_id") int supplierId);

    @Procedure(procedureName = "complete_order_items")
    void completeOrderItems(@Param("p_order_id") int orderId, @Param("p_supplier_id") int supplierId);

    @Procedure(procedureName = "cancel_order_items_buyer")
    void cancelOrderItemsBuyer(@Param("p_order_id") int orderId, @Param("p_buyer_id") int buyerId);

    @Procedure(procedureName = "cancel_order_items_supplier")
    void cancelOrderItemsSupplier(@Param("p_order_id") int orderId, @Param("p_supplier_id") int supplierId);

    List<Order> findAllByBuyer(User buyer);

    @Query("FROM Order o " +
            "JOIN OrderItem oi ON oi.order = o " +
            "JOIN Product p ON oi.product = p " +
            "WHERE p.supplier = :supplier " +
            "AND o.orderStatus = :orderStatus")
    List<Order> findAllBySupplierAndOrderStatus(User supplier, OrderStatus orderStatus);

    Optional<Order> findByIdAndBuyer(int oId, User buyer);

    @Query("FROM Order o " +
            "JOIN OrderItem oi on oi.order = o " +
            "JOIN Product p ON oi.product = p " +
            "WHERE o.id = :oId " +
            "AND p.supplier = :supplier")
    Optional<Order> findByIdAndSupplier(int oId, User supplier);
}
