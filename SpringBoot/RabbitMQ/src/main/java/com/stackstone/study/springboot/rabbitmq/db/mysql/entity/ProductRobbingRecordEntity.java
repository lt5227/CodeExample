package com.stackstone.study.springboot.rabbitmq.db.mysql.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * ProductRobbingRecordEntity.java
 *
 * <p>Copyright (c) 2020 StackStone All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020-10-19 17:19:49
 * @since 1.0.0
 */
@Entity
@Table(name = "product_robbing_record")
@Data
public class ProductRobbingRecordEntity {
    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    @Column(name = "id")
    private int id;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "product_id")
    private Integer productId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "robbing_time")
    private LocalDateTime robbingTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
