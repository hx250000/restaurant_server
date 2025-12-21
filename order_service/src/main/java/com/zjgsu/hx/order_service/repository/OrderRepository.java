package com.zjgsu.hx.order_service.repository;

import com.zjgsu.hx.order_service.model.Order;
import com.zjgsu.hx.order_service.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    /** 根据用户查询订单 */
    List<Order> findByUserIdOrderByCreateTimeDesc(Long userId);

    /** 根据订单状态查询 */
    List<Order> findByStatus(OrderStatus status);

    /** 根据订单号查询 */
    Order findByOrderNo(String orderNo);
}
