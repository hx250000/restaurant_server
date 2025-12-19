package com.zjgsu.hx.dish_service;

import com.zjgsu.hx.dish_service.model.Dish;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DishServiceApplicationTests {

	@Test
	void contextLoads() {
		Dish dish = new Dish();
		dish.setDishname("Test Dish");
		dish.setDescription("This is a test dish.");
		dish.setPrice(9.99);
		dish.setImageUrl("");
		dish.setSpicy(false);
		dish.setStock(100);
		dish.setStatus(1);
		System.out.println(dish);
	}

}
