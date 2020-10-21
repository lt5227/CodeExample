package com.stackstone.study.springboot.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * BasicQueueListener
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020 /10/21 12:36
 * @since 1.0.0
 */
@Component
@Slf4j
public class BasicQueueListener {

    /**
     * 监听消费消息(单个侦听器容器)
     * Consume message.
     * <p>
     * 此测试监听消息，需要将singleListenerContainer Bean初始化中：
     * factory.setMessageConverter(new Jackson2JsonMessageConverter());方法注释掉
     *
     * @param message the message
     */
    @RabbitListener(queues = "${mq.basic.info.queue}", containerFactory = "singleListenerContainerNotSetMessageConverter")
    public void consumeMessage01(@Payload byte[] message) {
        // 接收String
        String result = new String(message, StandardCharsets.UTF_8);
        log.info("接收到消息：{}", result);
    }
}
