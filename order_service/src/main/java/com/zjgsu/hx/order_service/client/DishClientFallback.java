package com.zjgsu.hx.order_service.client;

import com.zjgsu.hx.order_service.common.ApiResponse;
import com.zjgsu.hx.order_service.dto.DishDTO;
import org.springframework.stereotype.Component;

//@Component
public class DishClientFallback implements DishClient {
    @Override
    public ApiResponse<DishDTO> getDishById(Long id) {
        System.out.println("Dish service is unavailable. Fallback method invoked for dish ID: " + id);
        return ApiResponse.error(503,"Dish service is currently unavailable. Please try again later. (DishClientFallback)");
    }

    @Override
    public void decreaseStock(Long id, Integer reduceStock) {
        System.out.println("Dish service is unavailable. Cannot decrease stock for dish ID: " + id);
    }
}
