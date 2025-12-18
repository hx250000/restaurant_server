package com.zjgsu.hx.user_service.model.frontend;

import com.zjgsu.hx.user_service.model.UserRole;
import lombok.Data;

@Data
public class UserRegister {
    private String username;
    private String password;
    private String phone;
    private String address;
    private UserRole userrole;
}
