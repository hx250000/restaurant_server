package com.zjgsu.hx.order_service.client;

import com.zjgsu.hx.order_service.common.ApiResponse;
import com.zjgsu.hx.order_service.dto.UserDTO;
import org.springframework.stereotype.Component;

//@Component
public class UserClientFallback implements UserClient {
    @Override
    public ApiResponse<UserDTO> getUserById(Long id) {
        System.out.println("User service is unavailable. Fallback method invoked for user ID: " + id);
        return ApiResponse.error(503,"User service is currently unavailable. Please try again later. (UserClientFallback)");
    }
}
