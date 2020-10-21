package com.stackstone.study.springboot.rabbitmq.config.queue;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * AckAndConcurrentQueueConfig
 * 确认和并发队列配置
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 16:53
 * @since 1.0.0
 */
@Configuration
public class AckAndConcurrentQueueConfig {
    @Autowired
    private Environment env;

    /* 确认和并发队列消息模型构建 */

    /**
     * 定义交换机
     */
    @Bean
    public TopicExchange ackConcurrentExchange() {
        return new TopicExchange(env.getProperty("mq.ackConcurrent.info.exchange"), true, false);
    }

    /**
     * 定义队列
     */
    @Bean
    public Queue ackConcurrentQueue() {
        return new Queue(Objects.requireNonNull(env.getProperty("mq.ackConcurrent.info.queue")), true);
    }

    /**
     * 交换机根据RoutingKey和队列绑定
     */
    @Bean
    public Binding ackConcurrentBinding() {
        return BindingBuilder.bind(ackConcurrentQueue()).to(ackConcurrentExchange())
                .with(env.getProperty("mq.ackConcurrent.info.routingKey"));
    }
}
