package com.zjgsu.hx.order_service.client;

import com.zjgsu.hx.order_service.common.ApiResponse;
import com.zjgsu.hx.order_service.dto.DishDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@FeignClient(name = "dish-service")
public interface DishClient {
//    private static final String DISH_SERVICE_URL = "http://localhost:8082";
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    public DishDTO getDishById(Long dishId) {
//        ApiResponse<DishDTO> response =
//                restTemplate.exchange(
//                        DISH_SERVICE_URL + "/api/dishes/" + dishId,
//                        HttpMethod.GET,
//                        null,
//                        new ParameterizedTypeReference<ApiResponse<DishDTO>>() {}
//                ).getBody();
//
//        return response != null ? response.getData() : null;
//    }
//
//    public DishDTO decreaseStock(Long dishId, Integer quantity) {
//        return restTemplate.exchange(
//                DISH_SERVICE_URL + "/api/dishes/" + dishId + "/stock/reduce?reduceStock=" + quantity,
//                HttpMethod.PUT,
//                null,
//                DishDTO.class
//        ).getBody();
//    }
    @GetMapping("/api/dishes/{id}")
    ApiResponse<DishDTO> getDishById(@PathVariable("id") Long id);

    @PutMapping("/api/dishes/{id}/stock/reduce")
    void decreaseStock(
            @PathVariable("id") Long id,
            @RequestParam("reduceStock") Integer reduceStock
    );
}
