package com.stackstone.study.springboot.rabbitmq.config.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * RobbingQueueConfig
 * 抢单消息队列
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 16:11
 * @since 1.0.0
 */
@Configuration
public class RobbingQueueConfig {
    @Autowired
    private Environment env;

    /* 抢单消息队列模型构建 */

    /**
     * 定义交换机
     */
    @Bean
    public DirectExchange robbingExchange() {
        return new DirectExchange(env.getProperty("mq.robbing.info.exchange"), true, false);
    }

    /**
     * 定义队列
     */
    @Bean
    public Queue robbingQueue() {
        return new Queue(Objects.requireNonNull(env.getProperty("mq.robbing.info.queue")), true);
    }

    /**
     * 交换机根据RoutingKey和队列绑定
     */
    @Bean
    public Binding robbingBinding() {
        return BindingBuilder.bind(robbingQueue()).to(robbingExchange()).with(env.getProperty("mq.robbing.info.routingKey"));
    }
}
