package com.stackstone.study.springboot.rabbitmq.config;

import com.stackstone.study.springboot.rabbitmq.consumer.UserOrderListener;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMqUserOrderListenerConfig
 * Rabbit Mq用户订单监听器配置
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020 /10/21 17:36
 * @since 1.0.0
 */
@Configuration
public class RabbitMqUserOrderListenerConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;
    @Autowired
    private RabbitProperties rabbitProperties;
    @Autowired
    private UserOrderListener userOrderListener;

    /**
     * Listener container user order simple message listener container.
     *
     * @param userOrderQueue the user order queue
     * @return the simple message listener container
     */
    @Bean
    public SimpleMessageListenerContainer listenerContainerUserOrder(@Qualifier("userOrderQueue") Queue userOrderQueue) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 并发配置
//        // 设置并发消费者
//        container.setConcurrentConsumers(rabbitProperties.getListener().getSimple().getConcurrency());
//        // 设置最大同时消费者
//        container.setMaxConcurrentConsumers(rabbitProperties.getListener().getSimple().getMaxConcurrency());
//        // 设置预取计数
//        container.setPrefetchCount(rabbitProperties.getListener().getSimple().getPrefetch());

        // 消息确认（确认机制种类） 手动配置
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setQueues(userOrderQueue);
        container.setMessageListener(userOrderListener);
        return container;
    }
}
