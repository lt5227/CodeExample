package com.stackstone.study.springboot.rabbitmq.config.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * DeadLetterSimpleQueueConfig
 * 简单的死信队列配置
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020 /10/21 21:21
 * @since 1.0.0
 */
@Configuration
public class DeadLetterSimpleQueueConfig {

    @Autowired
    private Environment env;

    /* 死信队列消息模型 */

    // 创建死信队列

    /**
     * Simple dead queue queue.
     * 定义死信队列要设置DLX, DLK, TTL
     * DLX: 死信交换机
     * DLK: 死信RoutingKey
     * TTL: 存活时间
     *
     * @return the queue
     */
    @Bean
    public Queue simpleDeadQueue() {
        Map<String, Object> args = new HashMap<>(3);
        args.put("x-dead-letter-exchange", env.getProperty("mq.deadQueue.simple.deadVirtual.exchange"));
        args.put("x-dead-letter-routing-key", env.getProperty("mq.deadQueue.simple.deadVirtual.routingKey"));
        args.put("x-message-ttl", 10000);
        return new Queue(Objects.requireNonNull(env.getProperty("mq.deadQueue.simple.deadVirtual.queue")),
                true, false, false, args);
    }

    // 绑定死信队列——面向生产端

    /**
     * Simple dead exchange topic exchange.
     *
     * @return the topic exchange
     */
    @Bean
    public TopicExchange simpleDeadExchange() {
        return new TopicExchange(env.getProperty("mq.deadQueue.simple.produce.exchange"),
                true, false);
    }

    /**
     * Simple dead binding binding.
     *
     * @return the binding
     */
    @Bean
    public Binding simpleDeadBinding() {
        return BindingBuilder.bind(simpleDeadQueue())
                .to(simpleDeadExchange())
                .with(env.getProperty("mq.deadQueue.simple.produce.routingKey"));
    }


    // 创建并绑定实际监听消费队列

    /**
     * Simple dead real queue queue.
     *
     * @return the queue
     */
    @Bean
    public Queue simpleDeadRealQueue() {
        return new Queue(Objects.requireNonNull(env.getProperty("mq.deadQueue.simple.produce.queue")), true);
    }

    /**
     * 这里定义死信交换机
     * Simple dead real exchange topic exchange.
     *
     * @return the topic exchange
     */
    @Bean
    public TopicExchange simpleDeadRealExchange() {
        return new TopicExchange(env.getProperty("mq.deadQueue.simple.deadVirtual.exchange"),
                true, false);
    }

    /**
     * 死信交换机+死信RoutingKey 绑定真实的队列
     * Simple dead real binding binding.
     *
     * @return the binding
     */
    @Bean
    public Binding simpleDeadRealBinding() {
        return BindingBuilder.bind(simpleDeadRealQueue()).to(simpleDeadRealExchange())
                .with(env.getProperty("mq.deadQueue.simple.deadVirtual.routingKey"));
    }

}
