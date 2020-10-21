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
 * UserOrderTimeOutDeadQueueConfig
 * 用户下单支付超时死信队列模型
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020 /10/21 22:47
 * @since 1.0.0
 */
@Configuration
public class DeadLetterUserOrderQueueConfig {
    @Autowired
    private Environment env;

    /* 死信队列消息模型 */

    // 创建死信队列

    /**
     * 定义死信队列要设置DLX, DLK, TTL
     * DLX: 死信交换机
     * DLK: 死信RoutingKey
     * TTL: 存活时间
     *
     * @return the queue
     */
    @Bean
    public Queue userOrderDeadQueue() {
        Map<String, Object> args = new HashMap<>(3);
        args.put("x-dead-letter-exchange", env.getProperty("mq.deadQueue.userOrder.deadVirtual.exchange"));
        args.put("x-dead-letter-routing-key", env.getProperty("mq.deadQueue.userOrder.deadVirtual.routingKey"));
        args.put("x-message-ttl", 10000);
        return new Queue(Objects.requireNonNull(env.getProperty("mq.deadQueue.userOrder.deadVirtual.queue")),
                true, false, false, args);
    }

    // 绑定死信队列——面向生产端

    /**
     * User order dead exchange topic exchange.
     *
     * @return the topic exchange
     */
    @Bean
    public TopicExchange userOrderDeadExchange() {
        return new TopicExchange(env.getProperty("mq.deadQueue.userOrder.produce.exchange"),
                true, false);
    }

    /**
     * User order dead binding binding.
     *
     * @return the binding
     */
    @Bean
    public Binding userOrderDeadBinding() {
        return BindingBuilder.bind(userOrderDeadQueue())
                .to(userOrderDeadExchange())
                .with(env.getProperty("mq.deadQueue.userOrder.produce.routingKey"));
    }


    // 创建并绑定实际监听消费队列

    /**
     * User order dead real queue queue.
     *
     * @return the queue
     */
    @Bean
    public Queue userOrderDeadRealQueue() {
        return new Queue(Objects.requireNonNull(env.getProperty("mq.deadQueue.userOrder.produce.queue")), true);
    }

    /**
     * 这里定义死信交换机
     *
     * @return the topic exchange
     */
    @Bean
    public TopicExchange userOrderDeadRealExchange() {
        return new TopicExchange(env.getProperty("mq.deadQueue.userOrder.deadVirtual.exchange"),
                true, false);
    }

    /**
     * 死信交换机+死信RoutingKey 绑定真实的队列
     *
     * @return the binding
     */
    @Bean
    public Binding userOrderDeadRealBinding() {
        return BindingBuilder.bind(userOrderDeadRealQueue()).to(userOrderDeadRealExchange())
                .with(env.getProperty("mq.deadQueue.userOrder.deadVirtual.routingKey"));
    }
}
