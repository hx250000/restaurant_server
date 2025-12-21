package com.zjgsu.hx.order_service.client;

import com.zjgsu.hx.order_service.common.ApiResponse;
import com.zjgsu.hx.order_service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@FeignClient(name = "user-service")
public interface UserClient {
//    private static final String USER_SERVICE_URL = "http://localhost:8081";
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    public UserDTO getUserById(Long userId) {
//            ApiResponse<UserDTO> user=restTemplate.exchange(
//                USER_SERVICE_URL + "/api/users/" + userId,
//                    HttpMethod.GET,
//                    null,
//                    new ParameterizedTypeReference<ApiResponse<UserDTO>>() {}
//            ).getBody();
//            return user== null ? null : user.getData();
//    }
//
    @GetMapping("/api/users/{id}")
    ApiResponse<UserDTO> getUserById(@PathVariable("id") Long id);
}
