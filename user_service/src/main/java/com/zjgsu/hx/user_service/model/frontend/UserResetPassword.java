package com.zjgsu.hx.user_service.model.frontend;

import lombok.Data;

@Data
public class UserResetPassword {
    private String userName;
    private String newPassword;
}
