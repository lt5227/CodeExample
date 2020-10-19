package com.stackstone.study.springboot.rabbitmq.db.mysql.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * UserOrderEntity.java
 *
 * <p>Copyright (c) 2020 StackStone All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020-10-19 17:20:23
 * @since 1.0.0
 */
@Entity
@Table(name = "user_order")
@Data
public class UserOrderEntity {
    @Id
    @GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "increment")
    @Column(name = "id")
    private int id;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "status")
    private Integer status;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
