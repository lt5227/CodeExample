package com.stackstone.study.springboot.rabbitmq.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Copyright 2020 Oriental Standard All rights reserved.
 *
 * @author: LiLei
 * @className: SwaggerProperties
 * @createTime: 2020/10/19 11:52
 * @description: TODO
 */
@Component
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerProperties {
    /**
     * 项目名称
     */
    String name;

    /**
     * 标题
     */
    String title;

    /**
     * 作者
     */
    String author;

    /**
     * 项目地址
     */
    String url;

    /**
     * 邮箱
     */
    String email;

    /**
     * 项目描述
     */
    String description;

    /**
     * 版本
     */
    String version;
}
