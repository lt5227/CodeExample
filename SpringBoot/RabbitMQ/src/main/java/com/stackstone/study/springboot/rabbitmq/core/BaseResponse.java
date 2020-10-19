package com.stackstone.study.springboot.rabbitmq.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BaseResponse.java
 *
 * <p>Copyright (c) 2020 StackStone All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020-10-19 17:21:41
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    private Integer code;
    private String msg;
    private T data;

    public BaseResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(StatusCodeEnum statusCodeEnum, T data) {
        this.code = statusCodeEnum.getCode();
        this.msg = statusCodeEnum.getMsg();
        this.data = data;
    }

    public BaseResponse(StatusCodeEnum statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }
}
