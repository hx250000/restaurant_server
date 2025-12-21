package com.zjgsu.hx.order_service.controller;


import com.zjgsu.hx.order_service.common.ApiResponse;
import com.zjgsu.hx.order_service.model.Order;
import com.zjgsu.hx.order_service.model.frontend.OrderRequest;
import com.zjgsu.hx.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 查询所有订单（管理员专用接口，实际项目中应有权限控制）
     */
    @GetMapping("/admin/list")
    public ApiResponse<List<Order>> getAllOrdersByAdmin() {
        List<Order> orders = orderService.getAllOrders();
        return ApiResponse.success(orders);
    }

    /**
     * 1️⃣ 创建订单（下单）
     */
    @PostMapping
    public ApiResponse<Order> createOrder(@RequestBody OrderRequest request) {
        Order order = orderService.createOrder(request);
        return ApiResponse.success(order);
    }

    /**
     * 2️⃣ 查询订单详情
     */
    @GetMapping("/{orderId}")
    public ApiResponse<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ApiResponse.success(order);
    }

    /**
     * 3️⃣ 完成订单（确认收货 / 订单完成）
     */
    @PutMapping("/{orderId}/finish")
    public ApiResponse<Order> finishOrder(@PathVariable Long orderId) {
        Order order = orderService.completeOrder(orderId);
        return ApiResponse.success(order);
    }
}
