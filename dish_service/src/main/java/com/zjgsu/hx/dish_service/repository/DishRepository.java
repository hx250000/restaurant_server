package com.zjgsu.hx.dish_service.repository;

import com.zjgsu.hx.dish_service.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    /** 查询上架的所有菜品 */
    List<Dish> findByStatus(Integer status);

    @Query("SELECT d FROM Dish d WHERE d.dishname=:name and d.status = 1")
    Optional<Dish> findByDishname(String name);

    @Query("SELECT d FROM Dish d WHERE d.dishname LIKE %:keyword% AND d.status = 1")
    List<Dish> findByDishnameKeyword(String keyword);

//    @Query("SELECT d FROM Dish d WHERE d.dishname = :dishname AND d.status = 1")
    boolean existsByDishnameAndStatus(String dishname, Integer status);
}
