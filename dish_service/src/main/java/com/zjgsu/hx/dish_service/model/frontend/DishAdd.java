package com.zjgsu.hx.dish_service.model.frontend;

import lombok.Data;

@Data
public class DishAdd {
    private String dishname;
    private String description;
    private Double price;
    private String imageUrl;
    private boolean isSpicy;
    private Integer stock;
}
