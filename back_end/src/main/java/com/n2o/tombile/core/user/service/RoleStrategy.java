package com.n2o.tombile.core.user.service;

import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.product.order.model.order.Order;
import com.n2o.tombile.product.order.model.order.OrderHistory;
import com.n2o.tombile.product.order.model.order_item.OrderItem;
import com.n2o.tombile.product.order.model.order_item.OrderHistoryItem;
import com.n2o.tombile.product.order.repository.OrderHistoryRepository;
import com.n2o.tombile.product.order.repository.OrderItemHistoryRepository;
import com.n2o.tombile.product.order.repository.OrderItemRepository;
import com.n2o.tombile.product.order.repository.OrderRepository;

import java.util.List;

public interface RoleStrategy {
    String handleAfterVerifyEmail(User user);

    void cancelOrderItems(OrderRepository repository, int orderId);

    List<OrderHistory> getOrdersHistory(OrderHistoryRepository repository);

    OrderHistory getOrderHistoryById(OrderHistoryRepository repository, int orderId);

    Order getOrderById(OrderRepository repository, int orderId);

    List<OrderItem> getOrderItems(OrderItemRepository repository, int orderId);

    List<OrderHistoryItem> getOrderHistoryItems(OrderItemHistoryRepository repository, int orderId);
}
