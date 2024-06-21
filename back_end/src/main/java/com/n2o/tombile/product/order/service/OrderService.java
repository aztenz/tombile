package com.n2o.tombile.product.order.service;

import com.n2o.tombile.core.common.util.Util;
import com.n2o.tombile.core.user.model.User;
import com.n2o.tombile.core.user.service.RoleStrategyFactory;
import com.n2o.tombile.product.order.dto.*;
import com.n2o.tombile.product.order.model.OrderStatus;
import com.n2o.tombile.product.order.model.order.Order;
import com.n2o.tombile.product.order.model.order.OrderHistory;
import com.n2o.tombile.product.order.model.order.OrderModel;
import com.n2o.tombile.product.order.model.order_item.OrderItemModel;
import com.n2o.tombile.product.order.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static com.n2o.tombile.core.common.util.Constants.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemHistoryRepository orderItemHistoryRepository;
    private final RoleStrategyFactory roleStrategyFactory;

    public RSPOrder placeOrder(int addressId) {
        return OrderServiceHelper.executeOrderOperation(() -> {
            int orderId = orderRepository.createOrder(Util.getCurrentUserId(), addressId);
            return OrderServiceHelper.createPersistOrderResponse(orderId, ORDER_PLACED);
        });
    }

    public RSPOrder confirmOrderItems(int oId) {
        return OrderServiceHelper.executeOrderOperation(() -> {
            orderRepository.confirmOrderItems(oId, Util.getCurrentUserId());
            return OrderServiceHelper.createPersistOrderResponse(oId, ORDER_CONFIRMED);
        });
    }

    public RSPOrder rejectOrderItems(int oId) {
        return OrderServiceHelper.executeOrderOperation(() -> {
            orderRepository.rejectOrderItems(oId, Util.getCurrentUserId());
            return OrderServiceHelper.createPersistOrderResponse(oId, ORDER_REJECTED);
        });
    }

    public RSPOrder completeOrderItem(int oId) {
        return OrderServiceHelper.executeOrderOperation(() -> {
            orderRepository.completeOrderItems(oId, Util.getCurrentUserId());
            return OrderServiceHelper.createPersistOrderResponse(oId, ORDER_COMPLETED);
        });
    }

    public RSPOrder cancelOrder(int oId) {
        return OrderServiceHelper.executeOrderOperation(() -> {
            roleStrategyFactory
                    .getStrategy(Util.getCurrentUser().getUserData().getRole())
                    .cancelOrderItems(orderRepository, oId);
            return OrderServiceHelper.createPersistOrderResponse(oId, ORDER_CANCELLED);
        });
    }

    public List<RSPOrderListItem> getBuyerCurrentOrders() {
        return OrderServiceHelper
                .prepareOrdersList(orderRepository.findAllByBuyer(Util.getCurrentUser()));
    }

    public List<RSPOrderListItem> getSupplierPendingOrders() {
        return OrderServiceHelper.prepareOrdersList(OrderServiceHelper.getSupplierOrdersByStatus(orderRepository, Util.getCurrentUser(), OrderStatus.PENDING));
    }

    public List<RSPOrderListItem> getSupplierConfirmedOrders() {
        return OrderServiceHelper.prepareOrdersList(OrderServiceHelper.getSupplierOrdersByStatus(orderRepository, Util.getCurrentUser(), OrderStatus.CONFIRMED));
    }

    public List<RSPOrderListItem> getOrdersHistory() {
        return OrderServiceHelper.prepareOrdersList(OrderServiceHelper.getOrderHistoryEntities(roleStrategyFactory, orderHistoryRepository, Util.getCurrentUser()));
    }

    public RSPOrderDetails getOrderDetailsById(int oId) {
        return OrderServiceHelper.prepareOrderDetails(OrderServiceHelper.getOrderEntityById(roleStrategyFactory, orderRepository, oId), roleStrategyFactory
                .getStrategy(Util.getCurrentUser().getUserData().getRole())
                .getOrderItems(orderItemRepository, oId));
    }

    public RSPOrderDetails getOrderHistoryDetailsById(int oId) {
        return OrderServiceHelper.prepareOrderDetails(OrderServiceHelper.getOrderHistoryEntityById(roleStrategyFactory, orderHistoryRepository, oId), roleStrategyFactory
                .getStrategy(Util.getCurrentUser().getUserData().getRole())
                .getOrderHistoryItems(orderItemHistoryRepository, oId));
    }

    @FunctionalInterface
    public interface OrderOperation {
        RSPOrder execute() throws Exception;
    }

    private static class OrderServiceHelper {

        public static RSPOrder createPersistOrderResponse(int oId, String message) {
            RSPOrder response = new RSPOrder();
            response.setId(oId);
            response.setMessage(message);
            return response;
        }

        public static RSPOrder executeOrderOperation(OrderOperation operation) {
            try {
                return operation.execute();
            } catch (Exception e) {
                OrderUtil.handleException(e);
                throw new RuntimeException(e);
            }
        }

        public static List<Order> getSupplierOrdersByStatus(
                OrderRepository orderRepository,
                User user,
                OrderStatus orderStatus
        ) {
            return orderRepository.findAllBySupplierAndOrderStatus(user, orderStatus);
        }

        public static List<OrderHistory> getOrderHistoryEntities(
                RoleStrategyFactory roleStrategyFactory,
                OrderHistoryRepository orderHistoryRepository,
                User user
        ) {
            return roleStrategyFactory
                    .getStrategy(user.getUserData().getRole())
                    .getOrdersHistory(orderHistoryRepository);
        }

        public static Order getOrderEntityById(RoleStrategyFactory roleStrategyFactory, OrderRepository orderRepository, int oId) {
            return roleStrategyFactory.getStrategy(Util.getCurrentUser().getUserData().getRole()).getOrderById(orderRepository, oId);
        }

        public static OrderHistory getOrderHistoryEntityById(RoleStrategyFactory roleStrategyFactory, OrderHistoryRepository orderHistoryRepository, int oId) {
            return roleStrategyFactory.getStrategy(Util.getCurrentUser().getUserData().getRole()).getOrderHistoryById(orderHistoryRepository, oId);
        }

        public static <P extends RSPOrderListItem> P prepareOrderData(OrderModel order, Class<P> pClass) {
            RSPShippingAddress shippingAddress = Util.cloneObject(order.getShippingAddress(), RSPShippingAddress.class);
            P orderListItem = createInstance(pClass);
            orderListItem.setShippingAddress(shippingAddress);
            orderListItem.setOrderStatus(order.getOrderStatus().name());
            orderListItem.setOrderDate(order.getOrderDate());
            orderListItem.setBuyerEmail(order.getBuyer().getUserData().getEmail());
            orderListItem.setBuyerName(getUserName(order.getBuyer()));
            orderListItem.setTotalPrice(order.getTotalPrice());
            orderListItem.setId(order.getId());
            return orderListItem;
        }

        public static <P extends RSPOrderListItem> P createInstance(Class<P> pClass) {
            try {
                return pClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(ERROR_CREATE_INSTANCE + pClass.getName(), e);
            }
        }

        public static <O extends OrderItemModel> void prepareOrderItems(RSPOrderDetails response, List<O> items) {
            List<RSPOrderItem> orderItems = new ArrayList<>();
            items.forEach(item -> orderItems.add(prepareOrderItem(item)));
            response.setOrderItems(orderItems);
        }

        public static <O extends OrderItemModel> RSPOrderItem prepareOrderItem(O item) {
            RSPOrderItem orderItem = new RSPOrderItem();
            orderItem.setProductName(item.getProduct().getName());
            orderItem.setDescription(item.getProduct().getDescription());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getProduct().getPrice());
            orderItem.setSubtotal(item.getSubtotal());
            orderItem.setSupplierName(getUserName(item.getProduct().getSupplier()));
            orderItem.setSupplierEmail(item.getProduct().getSupplier().getUserData().getEmail());
            orderItem.setItemStatus(item.getItemStatus().name());
            return orderItem;
        }

        public static <O extends OrderModel> List<RSPOrderListItem> prepareOrdersList(List<O> orders) {
            List<RSPOrderListItem> responses = new ArrayList<>();
            orders.forEach(order -> responses.add(prepareOrderData(order, RSPOrderListItem.class)));
            return responses;
        }

        public static String getUserName(User user) {
            return user.getUserData().getFirstName() + " " + user.getUserData().getLastName();
        }

        public static RSPOrderDetails prepareOrderDetails(OrderModel order, List<? extends OrderItemModel> orderItems) {
            RSPOrderDetails response = prepareOrderData(order, RSPOrderDetails.class);
            prepareOrderItems(response, orderItems);
            return response;
        }
    }
}
