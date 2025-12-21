package com.zjgsu.hx.order_service.model.frontend;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long userId;
    private List<OrderItemDTO> items;
    private String remark;
}
