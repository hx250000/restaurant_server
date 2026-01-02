package com.zjgsu.hx.order_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders") // 注意表名不要和 SQL 关键字冲突
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 业务订单号 */
    @Column(unique = true, nullable = false)
    private String orderNo;

    /** 下单用户 */
    @Column(nullable = false)
    private Long userId;

    /** 订单总价（系统计算） */
    @Column(nullable = false)
    private Double totalAmount;

    /** 订单状态：NEW / COMPLETED */
    @Column(nullable = false)
    private OrderStatus status;

    /** 订单备注 */
    private String remark;

    /** 配送地址 */
    private String address;

    /* 订单明细关联（非必需，可单独通过 Repository 查询） */
    //@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    //private List<OrderItem> items;

    /** 创建时间 */
    @Column(updatable = false)
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    @PrePersist
    public void onCreate() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updateTime = LocalDateTime.now();
    }

}
