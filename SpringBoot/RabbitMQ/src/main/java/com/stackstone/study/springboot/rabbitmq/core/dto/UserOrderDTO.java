package com.stackstone.study.springboot.rabbitmq.core.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * UserOrder
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 17:43
 * @since 1.0.0
 */
@Data
public class UserOrderDTO implements Serializable {

    private static final long serialVersionUID = 8538486385214783855L;

    private String orderNo;

    private Integer userId;
}
