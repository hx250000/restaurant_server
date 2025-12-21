package com.zjgsu.hx.order_service.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_item")
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 所属订单 */
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /** 菜品 ID */
    @Column(nullable = false)
    private Long dishId;

    /** 菜品名称快照 */
    @Column(nullable = false)
    private String dishName;

    /** 下单时价格 */
    @Column(nullable = false)
    private Double price;

    /** 数量 */
    @Column(nullable = false)
    private Integer quantity;

    /** 小计 */
    @Column(nullable = false)
    private Double subtotal;
}
