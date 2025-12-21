package com.zjgsu.hx.order_service.client;

import com.zjgsu.hx.order_service.common.ApiResponse;
import com.zjgsu.hx.order_service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {
    private static final String USER_SERVICE_URL = "http://localhost:8081";

    @Autowired
    private RestTemplate restTemplate;

    public UserDTO getUserById(Long userId) {
            ApiResponse<UserDTO> user=restTemplate.exchange(
                USER_SERVICE_URL + "/api/users/" + userId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ApiResponse<UserDTO>>() {}
            ).getBody();
            return user== null ? null : user.getData();
    }
    /*
    ApiResponse<DishDTO> response =
                restTemplate.exchange(
                        DISH_SERVICE_URL + "/api/dishes/" + dishId,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ApiResponse<DishDTO>>() {}
                ).getBody();

        return response != null ? response.getData() : null;

     */
}
