package com.stackstone.study.springboot.rabbitmq.db.mysql.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * ProductBakEntity.java
 *
 * <p>Copyright (c) 2020 StackStone All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020-10-19 17:19:11
 * @since 1.0.0
 */
@Entity
@Table(name = "product_bak")
@Data
public class ProductBakEntity {

    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "purchase_date")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime purchaseDate;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "is_active")
    private Integer isActive;
}
