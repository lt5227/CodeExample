package com.stackstone.study.springboot.rabbitmq.db.mysql.repository;

import com.stackstone.study.springboot.rabbitmq.db.mysql.entity.ProductBakEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ProductBakRepository
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 15:10
 * @since 1.0.0
 */
public interface ProductBakRepository extends JpaRepository<ProductBakEntity, Integer> {
}
