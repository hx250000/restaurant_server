package com.zjgsu.hx.order_service.service;

import com.zjgsu.hx.order_service.client.DishClient;
import com.zjgsu.hx.order_service.client.UserClient;
import com.zjgsu.hx.order_service.dto.DishDTO;
import com.zjgsu.hx.order_service.dto.UserDTO;
import com.zjgsu.hx.order_service.exception.ResourceNotFoundException;
import com.zjgsu.hx.order_service.model.Order;
import com.zjgsu.hx.order_service.model.OrderItem;
import com.zjgsu.hx.order_service.model.OrderStatus;
import com.zjgsu.hx.order_service.model.frontend.OrderItemDTO;
import com.zjgsu.hx.order_service.model.frontend.OrderRequest;
import com.zjgsu.hx.order_service.repository.OrderItemRepository;
import com.zjgsu.hx.order_service.repository.OrderRepository;
import com.zjgsu.hx.order_service.utils.OrderNoUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private DishClient dishClient;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order createOrder(OrderRequest request) {

        // 1️⃣ 校验用户
        UserDTO user = userClient.getUserById(request.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在");
        }

        Double totalAmount = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDTO itemDTO : request.getItems()) {
            DishDTO dish = dishClient.getDishById(itemDTO.getDishId());
            System.out.println("Dish fetched: " + dish.toString());

            if (dish == null) {
                throw new ResourceNotFoundException("菜品不存在：" + itemDTO.getDishId());
            }

        }

        // 3️⃣ 统一扣库存（降低部分成功风险）
        for (OrderItemDTO itemDTO : request.getItems()) {
//            dishClient.decreaseStock(itemDTO.getDishId(), itemDTO.getQuantity());
            try {
                dishClient.decreaseStock(itemDTO.getDishId(), itemDTO.getQuantity());
            } catch (HttpClientErrorException e) {
                // 4xx → 业务异常
                throw new IllegalArgumentException("库存不足或菜品不可用"+e.getMessage());
            } catch (Exception e) {
                // 5xx / 网络问题
                throw new RuntimeException("库存服务不可用，请稍后重试"+e.getMessage());
            }

        }

        // 4️⃣ 组装订单项
        for (OrderItemDTO itemDTO : request.getItems()) {
            DishDTO dish = dishClient.getDishById(itemDTO.getDishId());

            Double subtotal = dish.getPrice() * itemDTO.getQuantity();

            OrderItem item = new OrderItem();
            item.setDishId(dish.getId());
            item.setDishName(dish.getDishname());
            item.setPrice(dish.getPrice());
            item.setQuantity(itemDTO.getQuantity());
            item.setSubtotal(subtotal);

            totalAmount = totalAmount+subtotal;
            orderItems.add(item);
        }

        Date now = new Date();
        // 4️⃣ 创建订单
        Order order = new Order();
        order.setOrderNo("ORD"+ OrderNoUtils.generateOrderNo() + user.getId());
        order.setUserId(user.getId());
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.CREATED);
        order.setRemark(request.getRemark());

        orderRepository.save(order);

        // 5️⃣ 保存订单项
        for (OrderItem item : orderItems) {
            item.setOrder(order); // 关联订单
        }
        orderItemRepository.saveAll(orderItems);

        return order;
    }

    public Order completeOrder(Long orderId) {
        Order order=orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("订单不存在"));
        order.setStatus(OrderStatus.FINISHED);
        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("订单不存在!"));
    }
}



