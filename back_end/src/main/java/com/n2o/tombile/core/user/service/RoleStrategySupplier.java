package com.n2o.tombile.core.user.service;

import com.n2o.tombile.core.common.exception.ItemNotFoundException;
import com.n2o.tombile.core.common.util.Util;
import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.core.user.model.VerificationStatus;
import com.n2o.tombile.product.order.model.order.Order;
import com.n2o.tombile.product.order.model.order.OrderHistory;
import com.n2o.tombile.product.order.model.order_item.OrderHistoryItem;
import com.n2o.tombile.product.order.model.order_item.OrderItem;
import com.n2o.tombile.product.order.repository.OrderHistoryRepository;
import com.n2o.tombile.product.order.repository.OrderItemHistoryRepository;
import com.n2o.tombile.product.order.repository.OrderItemRepository;
import com.n2o.tombile.product.order.repository.OrderRepository;
import com.n2o.tombile.product.product.model.Product;
import com.n2o.tombile.product.product.repository.ProductRepository;

import java.util.List;

import static com.n2o.tombile.core.common.util.Constants.EMAIL_SENT_TO_ADMIN;
import static com.n2o.tombile.core.common.util.Constants.ERROR_ORDER_NOT_FOUND;
import static com.n2o.tombile.core.common.util.Constants.ERROR_PRODUCT_NOT_FOUND;

public class RoleStrategySupplier implements RoleStrategy {

    @Override
    public String handleAfterVerifyEmail(User user) {
        user.getUserData().setVerificationStatus(VerificationStatus.VERIFIED);
        return EMAIL_SENT_TO_ADMIN;
    }

    @Override
    public void cancelOrderItems(OrderRepository repository, int orderId) {
        repository.cancelOrderItemsSupplier(orderId, Util.getCurrentUserId());
    }

    @Override
    public List<OrderHistory> getOrdersHistory(OrderHistoryRepository repository) {
        return repository.findAllBySupplier(Util.getCurrentUser());
    }

    @Override
    public OrderHistory getOrderHistoryById(OrderHistoryRepository repository, int orderId) {
        return repository
                .findByIdAndSupplier(orderId, Util.getCurrentUser())
                .orElseThrow(() -> new ItemNotFoundException(ERROR_ORDER_NOT_FOUND));
    }

    @Override
    public Order getOrderById(OrderRepository repository, int orderId) {
        return repository
                .findByIdAndSupplier(orderId, Util.getCurrentUser())
                .orElseThrow(() -> new ItemNotFoundException(ERROR_ORDER_NOT_FOUND));
    }

    @Override
    public List<OrderItem> getOrderItems(OrderItemRepository repository, int orderId) {
        return repository.findAllByOrderAndSupplier(orderId, Util.getCurrentUser());
    }

    @Override
    public List<OrderHistoryItem> getOrderHistoryItems(OrderItemHistoryRepository repository, int orderId) {
        return repository.findAllByOrderAndSupplier(orderId, Util.getCurrentUser());
    }

    @Override
    public <P extends Product, R extends ProductRepository<P>> List<P> getProducts(R repository) {
        return repository.findAllUserProducts(Util.getCurrentUserId(), repository.getProductType());
    }

    @Override
    public <P extends Product, R extends ProductRepository<P>> P getProductById(R repository, int productId) {
        return repository.findUserProductById(productId, Util.getCurrentUserId(), repository.getProductType())
                .orElseThrow(() -> new ItemNotFoundException(ERROR_PRODUCT_NOT_FOUND));
    }

}
