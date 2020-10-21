package com.stackstone.study.springboot.rabbitmq.core.dto;

import lombok.Data;

/**
 * Log
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 19:08
 * @since 1.0.0
 */
@Data
public class LogDTO {
    private String methodName;
    private String operateData;
}
