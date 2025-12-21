package com.zjgsu.hx.order_service.model.frontend;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long dishId;
    private Integer quantity;
}
