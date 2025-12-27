package com.zjgsu.hx.order_service.dto;

import lombok.Data;

@Data
public class OrderItemDetail {
    private Long dishId;
    private String dishName;
    private Double price;
    private Integer quantity;
    private Double subtotal;
}
