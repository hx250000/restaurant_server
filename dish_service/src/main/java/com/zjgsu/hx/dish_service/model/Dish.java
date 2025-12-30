package com.zjgsu.hx.dish_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "restaurant_dish")
@Data
public class Dish {
    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 菜品名称 */
    @Column(unique = true, nullable = false)
    private String dishname;

    /** 菜品描述 */
    @Column(length = 500)
    private String description;

    /* 分类 */
    @Column(nullable = false)
    private String category;

    /** 价格 */
    @Column(nullable = false)
    private Double price;

    /** 图片URL */
    private String imageUrl;

    /** 是否辣 */
    @Column(nullable = false)
    private boolean spicy;

    /** 当前库存 */
    @Column(nullable = false)
    private Integer stock;

    /** 上架状态：0-下架，1-上架 */
    @Column(nullable = false)
    private Integer status;

    /** 创建时间 */
    @Column(updatable = false)
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    @PrePersist
    public void onCreate() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.status = 1; // 默认上架
    }

    @PreUpdate
    public void onUpdate() {
        this.updateTime = LocalDateTime.now();
    }


}
