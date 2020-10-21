package com.stackstone.study.springboot.rabbitmq.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * SwaggerProperties.java
 *
 * <p>Copyright (c) 2020 StackStone All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020-10-20 10:43:40
 * @since 1.0.0
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
