package com.zjgsu.hx.order_service.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String phone;
    private String address;
    private UserRole role;
}
