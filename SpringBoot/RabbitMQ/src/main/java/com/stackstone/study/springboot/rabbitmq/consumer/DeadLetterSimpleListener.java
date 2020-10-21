package com.stackstone.study.springboot.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * DeadLetterSimpleListener
 * 简单的死信队列监听器
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020 /10/21 22:05
 * @since 1.0.0
 */
@Component
@Slf4j
public class DeadLetterSimpleListener {

    /**
     * 监听消费死信队列中的信息
     * Consume simple dead letter msg.
     *
     * @param msg the msg
     */
    @RabbitListener(queues = "${mq.deadQueue.simple.produce.queue}", containerFactory = "singleListenerContainer")
    public void consumeSimpleDeadLetterMsg(@Payload String msg) {
        try {
            log.info("监听消费死信队列中的消息: {}", msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
