package com.stackstone.study.springboot.rabbitmq.core;

import lombok.Getter;

/**
 * StatusCodeEnum.java
 *
 * <p>Copyright (c) 2020 StackStone All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020-10-19 17:21:51
 * @since 1.0.0
 */
@Getter
public enum StatusCodeEnum {

    /**
     * 成功
     */
    SUCCESS(0, "成功"),

    /**
     * 失败
     */
    FAIL(-1, "失败");

    private final Integer code;
    private final String msg;

    StatusCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
