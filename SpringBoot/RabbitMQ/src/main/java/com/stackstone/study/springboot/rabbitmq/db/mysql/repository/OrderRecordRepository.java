package com.stackstone.study.springboot.rabbitmq.db.mysql.repository;

import com.stackstone.study.springboot.rabbitmq.db.mysql.entity.OrderRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * OrderRecordRepository.java
 *
 * <p>Copyright (c) 2020 StackStone All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020-10-19 17:21:23
 * @since 1.0.0
 */
public interface OrderRecordRepository extends JpaRepository<OrderRecordEntity, Integer> {
}
