package com.zjgsu.hx.user_service.controller;

import com.zjgsu.hx.user_service.common.ApiResponse;
import com.zjgsu.hx.user_service.model.User;
import com.zjgsu.hx.user_service.model.frontend.UserLogin;
import com.zjgsu.hx.user_service.model.frontend.UserRegister;
import com.zjgsu.hx.user_service.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 查询全部用户
     */
    @GetMapping
    public ApiResponse<List<User>> findAll() {
        return ApiResponse.success(userService.findAll());
    }

    /**
     * 根据 ID 查询
     */
    @GetMapping("/{id}")
    public ApiResponse<User> findById(@PathVariable Long id) {
        return ApiResponse.success(userService.findById(id));
    }

    /**
     * 根据用户名查询（查不到不报错）
     */
    @GetMapping("/user")
    public ApiResponse<User> findByUsername(@RequestParam String username) {
        User user = userService.findByUsername(username);
        return ApiResponse.success(user); // 可能为 null
    }

    /**
     * 用户名模糊查询
     */
    @GetMapping("/search")
    public ApiResponse<List<User>> search(@RequestParam String keyword) {
        return ApiResponse.success(userService.findByUserNameKeyword(keyword));
    }

    /**
     * 用户注册（支持软删除恢复）
     */
    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody UserRegister userRegister) {
        return ApiResponse.success(userService.register(userRegister));
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/{id}")
    public ApiResponse<User> update(
            @PathVariable Long id,
            @RequestBody UserRegister userRegister) {
        return ApiResponse.success(userService.updateUser(id, userRegister));
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ApiResponse<User> login(
            @RequestBody UserLogin userLogin) {
        return ApiResponse.success(userService.authenticateUser(userLogin));
    }

    /**
     * 软删除用户
     */
    @DeleteMapping("/{id}")
    public ApiResponse<User> delete(@PathVariable Long id) {
        return ApiResponse.success(userService.deleteUserById(id));
    }
}
