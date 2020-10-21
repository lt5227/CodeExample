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
 * UserOrderQueueConfig
 * 用户订单队列配置
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 17:29
 * @since 1.0.0
 */
@Configuration
public class UserOrderQueueConfig {
    @Autowired
    private Environment env;

    /**
     * 定义交换机
     */
    @Bean
    public TopicExchange userOrderExchange() {
        return new TopicExchange(env.getProperty("mq.userOrder.info.exchange"), true, false);
    }

    /**
     * 定义队列
     */
    @Bean
    public Queue userOrderQueue() {
        return new Queue(Objects.requireNonNull(env.getProperty("mq.userOrder.info.queue")), true);
    }

    /**
     * 交换机根据RoutingKey和队列绑定
     */
    @Bean
    public Binding userOrderBinding() {
        return BindingBuilder.bind(userOrderQueue()).to(userOrderExchange())
                .with(env.getProperty("mq.userOrder.info.routingKey"));
    }
}
