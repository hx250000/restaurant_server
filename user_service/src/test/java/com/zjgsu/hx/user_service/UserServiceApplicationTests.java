package com.zjgsu.hx.user_service;

import com.zjgsu.hx.user_service.model.User;
import com.zjgsu.hx.user_service.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
		User usertest=new User();
		usertest.setId(1L);
		usertest.setUsername("testuser");
		usertest.setPassword("encryptedpassword");
		usertest.setPhone("1234567890");
		usertest.setAddress("123 Test St, Test City");
		usertest.setRole(UserRole.CUSTOMER);
		usertest.setIsDeleted(0);
		System.out.println("User created: " + usertest);
	}

}
