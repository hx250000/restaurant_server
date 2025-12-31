package com.zjgsu.hx.user_service.model.frontend;

import lombok.Data;

@Data
public class UserUpdate {
    private Integer id;
    private String username;
    private String phone;
    private String address;
}
