package com.zjgsu.hx.order_service.repository;

import com.zjgsu.hx.order_service.model.OrderItem;
import com.zjgsu.hx.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /** 查询某个订单的所有明细 */
    List<OrderItem> findByOrder(Order order);
}