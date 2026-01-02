package com.zjgsu.hx.order_service.dto;

import com.zjgsu.hx.order_service.model.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class OrderDetailDTO {
    private String orderNo;
    private Long userId;
    private String address;
    private String remark;
    //private String userName;
    private Double totalAmount;
    private OrderStatus status;
    private LocalDateTime createTime;
    private List<OrderItemDetail> items;
}
