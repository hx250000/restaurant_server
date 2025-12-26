package com.zjgsu.hx.dish_service.controller;

import com.zjgsu.hx.dish_service.common.ApiResponse;
import com.zjgsu.hx.dish_service.model.Dish;
import com.zjgsu.hx.dish_service.model.frontend.DishAdd;
import com.zjgsu.hx.dish_service.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 查询所有上架菜品
     */
    @GetMapping("/all")
    public ApiResponse<List<Dish>> getAllDishes() {
        return ApiResponse.success(dishService.getAllDishes());
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    public ApiResponse<Dish> getDishById(@PathVariable Long id) {
        return ApiResponse.success(dishService.getDishById(id));
    }

    /**
     * 根据菜品名查询
     */
    @GetMapping("/name/{name}")
    public ApiResponse<Dish> getDishByName(@PathVariable String name) {
        return ApiResponse.success(dishService.getDishByName(name));
    }

    /**
     * 根据关键字模糊查询
     */
    @GetMapping("/search")
    public ApiResponse<List<Dish>> searchDishes(@RequestParam String keyword) {
        return ApiResponse.success(dishService.getDishesByKeyword(keyword));
    }

    /**
     * 新增菜品
     */
    @PostMapping
    public ApiResponse<Dish> addDish(@RequestBody DishAdd dishAdd) {
        return ApiResponse.success(dishService.addDish(dishAdd));
    }

    /**
     * 更新菜品（全量更新）
     */
    @PutMapping("/{id}")
    public ApiResponse<Dish> updateDish(@PathVariable Long id,
                           @RequestBody DishAdd dishUpdate) {
        return ApiResponse.success(dishService.updateDish(id, dishUpdate));
    }

    /**
     * 逻辑删除菜品
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Dish> deleteDish(@PathVariable Long id) {
        return ApiResponse.success(dishService.deleteDishById(id));
    }

    /**
     * 直接设置库存
     */
    @PutMapping("/{id}/stock")
    public ApiResponse<Dish> updateDishStock(@PathVariable Long id,
                                @RequestParam Integer stock) {
        return ApiResponse.success(dishService.updateDishStock(id, stock));
    }

    /**
     * 增加库存
     */
    @PutMapping("/{id}/stock/add")
    public ApiResponse<Dish> addDishStock(@PathVariable Long id,
                             @RequestParam Integer addStock) {
        return ApiResponse.success(dishService.addDishStock(id, addStock));
    }

    /**
     * 减少库存
     */
    @PutMapping("/{id}/stock/reduce")
    public ApiResponse<Dish> reduceDishStock(@PathVariable Long id,
                                @RequestParam Integer reduceStock) {
        return ApiResponse.success(dishService.reduceDishStock(id, reduceStock));
    }
}
