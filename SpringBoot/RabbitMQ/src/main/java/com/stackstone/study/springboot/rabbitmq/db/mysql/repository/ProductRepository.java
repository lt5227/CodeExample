package com.stackstone.study.springboot.rabbitmq.db.mysql.repository;

import com.stackstone.study.springboot.rabbitmq.db.mysql.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * ProductRepository
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020 /10/21 15:11
 * @since 1.0.0
 */
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    /**
     * Find by product number product entity.
     *
     * @param productNo the product no
     * @return the product entity
     */
    ProductEntity findByProductNo(String productNo);

    /**
     * Update total int.
     *
     * @param productNo the product no
     * @return the int
     */
    @Modifying
    @Query("update ProductEntity p set p.total = p.total - 1 where p.productNo = :productNo and p.total > 0")
    @Transactional(rollbackOn = Exception.class)
    int updateTotal(@Param("productNo") String productNo);
}
