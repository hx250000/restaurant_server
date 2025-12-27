package com.zjgsu.hx.dish_service.service;

import com.zjgsu.hx.dish_service.exception.ResourceConflictException;
import com.zjgsu.hx.dish_service.exception.ResourceNotFoundException;
import com.zjgsu.hx.dish_service.model.Dish;
import com.zjgsu.hx.dish_service.model.frontend.DishAdd;
import com.zjgsu.hx.dish_service.repository.DishRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;

    // 查询所有上架的菜品
    public List<Dish> getAllDishes() {
        return dishRepository.findByStatus(1);
    }

    // 根据ID查询菜品，查不到时抛出异常
    public Dish getDishById(Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("菜品不存在或已删除！"));
    }

    // 根据名称查询菜品，查不到时返回null，（前端显示为空但不报错）
    public Dish getDishByName(String name) {
        return dishRepository.findByDishname(name).orElse(null);
    }

    public List<Dish> getDishesByKeyword(String keyword) {
        return dishRepository.findByDishnameKeyword(keyword);
    }

    public List<Dish> getDishesByCategory(String category) {
        return dishRepository.findDishesByCategory(category);
    }

    @Transactional
    public Dish addDish(DishAdd dishAdd) {
        Dish dish=new Dish();
        //唯一性校验
        if(dishAdd.getDishname()==null||dishAdd.getDishname().isEmpty()){
            throw new IllegalArgumentException("菜品名称不能为空！");
        }
        if(dishRepository.existsByDishnameAndStatus(dishAdd.getDishname(), 1)){
            Dish dishExisting=dishRepository.findByDishname(dishAdd.getDishname()).get();
            //菜品已上架
            if (dishExisting.getStatus()==1){
                throw new ResourceConflictException("菜品名已存在！");
            }
            //菜品逻辑删除状态，允许重新添加
            dishExisting.setDescription(dishAdd.getDescription());
            dishExisting.setPrice(dishAdd.getPrice());
            dishExisting.setCategory(dishAdd.getCategory());
            dishExisting.setImageUrl(dishAdd.getImageUrl());
            dishExisting.setSpicy(dishAdd.isSpicy());
            dishExisting.setStock(dishAdd.getStock());
            dishExisting.setStatus(1);
            return dishRepository.save(dishExisting);
        }
        dish.setDishname(dishAdd.getDishname());
        dish.setDescription(dishAdd.getDescription());
        dish.setPrice(dishAdd.getPrice());
        dish.setCategory(dishAdd.getCategory());
        dish.setImageUrl(dishAdd.getImageUrl());
        dish.setSpicy(dishAdd.isSpicy());
        dish.setStock(dishAdd.getStock());
        dish.setStatus(1);
        return dishRepository.save(dish);
    }

    @Transactional
    public Dish deleteDishById(Long id) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("菜品不存在或已删除！"));
        // 逻辑删除
        dish.setStatus(0);
        return dishRepository.save(dish);
    }

    @Transactional
    public Dish updateDish(Long id, DishAdd dishUpdate) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("菜品不存在或已删除！"));
        if(dishUpdate.getDishname()==null||dishUpdate.getDishname().isEmpty()){
            throw new IllegalArgumentException("菜品名称不能为空！");
        }
        if(!dish.getDishname().equals(dishUpdate.getDishname())){
            //菜品名称修改，进行唯一性校验
            if(dishRepository.existsByDishnameAndStatus(dishUpdate.getDishname(), 1)){
                throw new ResourceConflictException("菜品名已存在！");
            }
            dish.setDishname(dishUpdate.getDishname());
        }
        dish.setDescription(dishUpdate.getDescription());
        dish.setPrice(dishUpdate.getPrice());
        dish.setCategory(dishUpdate.getCategory());
        dish.setImageUrl(dishUpdate.getImageUrl());
        dish.setSpicy(dishUpdate.isSpicy());
        dish.setStock(dishUpdate.getStock());
        return dishRepository.save(dish);
    }

    @Transactional
    public Dish updateDishStock(Long id, Integer newStock) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("菜品不存在或已删除！"));
        dish.setStock(newStock);
        return dishRepository.save(dish);
    }

    @Transactional
    public Dish addDishStock(Long id, Integer addStock) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("菜品不存在或已删除！"));
        dish.setStock(dish.getStock()+addStock);
        return dishRepository.save(dish);
    }

    @Transactional
    public Dish reduceDishStock(Long id, Integer reduceStock) {
        Dish dish = dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("菜品不存在或已删除！"));
        if(dish.getStock()<reduceStock){
            throw new IllegalArgumentException("库存不足！");
        }
        dish.setStock(dish.getStock()-reduceStock);
        return dishRepository.save(dish);
    }
}
