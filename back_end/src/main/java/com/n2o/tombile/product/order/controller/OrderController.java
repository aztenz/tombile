package com.n2o.tombile.product.order.controller;

import com.n2o.tombile.product.order.dto.RSPOrder;
import com.n2o.tombile.product.order.dto.RSPOrderDetails;
import com.n2o.tombile.product.order.dto.RSPOrderListItem;
import com.n2o.tombile.product.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/place")
    public RSPOrder placeOrder(@RequestParam int addressId) {
        return orderService.placeOrder(addressId);
    }

    @PostMapping("/confirm")
    public RSPOrder confirmOrderItems(@RequestParam int oId) {
        return orderService.confirmOrderItems(oId);
    }

    @PostMapping("/reject")
    public RSPOrder rejectOrderItems(@RequestParam int oId) {
        return orderService.rejectOrderItems(oId);
    }

    @PostMapping("/complete")
    public RSPOrder completeOrderItem(@RequestParam int oId) {
        return orderService.completeOrderItem(oId);
    }

    @PostMapping("/cancel")
    public RSPOrder cancelOrder(@RequestParam int oId) {
        return orderService.cancelOrder(oId);
    }

    @GetMapping("/current")
    public List<RSPOrderListItem> getBuyerCurrentOrders() {
        return orderService.getBuyerCurrentOrders();
    }

    @GetMapping("/pending")
    public List<RSPOrderListItem> getSupplierPendingOrders() {
        return orderService.getSupplierPendingOrders();
    }

    @GetMapping("/confirmed")
    public List<RSPOrderListItem> getSupplierConfirmedOrders() {
        return orderService.getSupplierConfirmedOrders();
    }

    @GetMapping("/history")
    public List<RSPOrderListItem> getOrdersHistory() {
        return orderService.getOrdersHistory();
    }

    @GetMapping("/current/{id}")
    public RSPOrderDetails getOrderDetailsById(@PathVariable("id") int oId) {
        return orderService.getOrderDetailsById(oId);
    }

    @GetMapping("/history/{id}")
    public RSPOrderDetails getOrderHistoryDetailsById(@PathVariable("id") int oId) {
        return orderService.getOrderHistoryDetailsById(oId);
    }
}