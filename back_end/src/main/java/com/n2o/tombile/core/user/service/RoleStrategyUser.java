package com.n2o.tombile.core.user.service;

import com.n2o.tombile.core.common.exception.ItemNotFoundException;
import com.n2o.tombile.core.common.util.Util;
import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.core.user.model.VerificationStatus;
import com.n2o.tombile.product.order.model.order.Order;
import com.n2o.tombile.product.order.model.order.OrderHistory;
import com.n2o.tombile.product.order.model.order_item.OrderItem;
import com.n2o.tombile.product.order.model.order_item.OrderHistoryItem;
import com.n2o.tombile.product.order.repository.OrderHistoryRepository;
import com.n2o.tombile.product.order.repository.OrderItemHistoryRepository;
import com.n2o.tombile.product.order.repository.OrderItemRepository;
import com.n2o.tombile.product.order.repository.OrderRepository;

import java.util.List;

import static com.n2o.tombile.core.common.util.Constants.EMAIL_VERIFIED;
import static com.n2o.tombile.core.common.util.Constants.ERROR_ORDER_NOT_FOUND;

public class RoleStrategyUser implements RoleStrategy {

    @Override
    public String handleAfterVerifyEmail(User user) {
        user.getUserData().setVerificationStatus(VerificationStatus.APPROVED);
        return EMAIL_VERIFIED;
    }

    @Override
    public void cancelOrderItems(OrderRepository repository, int orderId) {
        repository.cancelOrderItemsBuyer(orderId, Util.getCurrentUserId());
    }

    @Override
    public List<OrderHistory> getOrdersHistory(OrderHistoryRepository repository) {
        return repository.findAllByBuyer(Util.getCurrentUser());
    }

    @Override
    public OrderHistory getOrderHistoryById(OrderHistoryRepository repository, int orderId) {
        return repository
                .findByIdAndBuyer(orderId, Util.getCurrentUser())
                .orElseThrow(() -> new ItemNotFoundException(ERROR_ORDER_NOT_FOUND));
    }

    @Override
    public Order getOrderById(OrderRepository repository, int orderId) {
        return repository
                .findByIdAndBuyer(orderId, Util.getCurrentUser())
                .orElseThrow(() -> new ItemNotFoundException(ERROR_ORDER_NOT_FOUND));
    }

    @Override
    public List<OrderItem> getOrderItems(OrderItemRepository repository, int orderId) {
        return repository.findAllByOrderId(orderId);
    }

    @Override
    public List<OrderHistoryItem> getOrderHistoryItems(OrderItemHistoryRepository repository, int orderId) {
        return repository.findAllByOrderId(orderId);
    }

}
