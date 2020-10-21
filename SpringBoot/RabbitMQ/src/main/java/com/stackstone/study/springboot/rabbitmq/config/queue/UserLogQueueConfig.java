package com.stackstone.study.springboot.rabbitmq.config.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * LogQueueConfig
 * 用户操作日志队列消息模型
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 19:13
 * @since 1.0.0
 */
@Configuration
public class UserLogQueueConfig {
    @Autowired
    private Environment env;

    /* 用户操作日志消息队列模型构建 */

    /**
     * 定义交换机
     */
    @Bean
    public TopicExchange userLogExchange() {
        return new TopicExchange(env.getProperty("mq.userLog.info.exchange"), true, false);
    }

    /**
     * 定义队列
     */
    @Bean
    public Queue userLogQueue() {
        return new Queue(Objects.requireNonNull(env.getProperty("mq.userLog.info.queue")), true);
    }

    /**
     * 交换机根据RoutingKey和队列绑定
     */
    @Bean
    public Binding userLogBinding() {
        return BindingBuilder.bind(userLogQueue()).to(userLogExchange()).with(env.getProperty("mq.userLog.info.routingKey"));
    }
}
