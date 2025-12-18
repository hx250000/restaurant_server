package com.zjgsu.hx.user_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "restaurant_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 用户名（唯一） */
    @Column(unique = true, nullable = false)
    private String username;

    /** 加密后的密码 */
    @Column(nullable = false)
    private String password;

//    /** 邮箱（唯一） */
//    @Column(unique = true, nullable = false)
//    private String email;
    /** 电话号码（唯一） */
    @Column(unique = true, nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    /** 角色：admin / staff / customer */
    @Enumerated(EnumType.STRING)
    private UserRole role;

    /** 用户状态：1-禁用，0-正常 */
    private int isDeleted;

    /** 创建时间 */
    @Column(updatable = false)
    private LocalDateTime createTime;

    @Column
    private LocalDateTime updateTime;

    @PrePersist
    public void onCreate() {
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.isDeleted = 0;
    }
}
