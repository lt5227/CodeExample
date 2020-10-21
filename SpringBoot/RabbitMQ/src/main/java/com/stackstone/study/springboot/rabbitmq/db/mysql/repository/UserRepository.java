package com.stackstone.study.springboot.rabbitmq.db.mysql.repository;

import com.stackstone.study.springboot.rabbitmq.db.mysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020 /10/21 15:12
 * @since 1.0.0
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    /**
     * Find by user name and password user entity.
     *
     * @param username the username
     * @param password the password
     * @return the user entity
     */
    UserEntity findByUserNameAndPassword(String username, String password);
}
