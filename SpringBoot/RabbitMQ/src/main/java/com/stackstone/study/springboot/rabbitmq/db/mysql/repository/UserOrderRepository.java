package com.stackstone.study.springboot.rabbitmq.db.mysql.repository;

import com.stackstone.study.springboot.rabbitmq.db.mysql.entity.UserOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserOrderRepository
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020 /10/21 15:13
 * @since 1.0.0
 */
public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Integer> {
    /**
     * Find by id and status user order entity.
     *
     * @param id     the id
     * @param status the status
     * @return the user order entity
     */
    UserOrderEntity findByIdAndStatus(Integer id, Integer status);
}
