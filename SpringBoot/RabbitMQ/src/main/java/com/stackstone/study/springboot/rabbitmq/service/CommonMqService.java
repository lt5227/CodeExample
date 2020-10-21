package com.stackstone.study.springboot.rabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * CommonMqService
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020 /10/21 16:16
 * @since 1.0.0
 */
@Service
@Slf4j
public class CommonMqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;

    /**
     * 发送抢单信息入队列
     * Send robbing msg.
     *
     * @param mobile the mobile
     */
    public void sendRobbingMsg(String mobile) {
        try {
            // 设置交换机
            rabbitTemplate.setExchange(env.getProperty("mq.robbing.info.exchange"));
            // 设置RoutingKey
            rabbitTemplate.setRoutingKey(Objects.requireNonNull(env.getProperty("mq.robbing.info.routingKey")));
            // 消息构建
            Message message = MessageBuilder.withBody(mobile.getBytes(StandardCharsets.UTF_8))
                    // 消息持久化
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .build();
            rabbitTemplate.send(message);
        } catch (Exception e) {
            log.error("发送抢单信息入队列 发送异常， mobile={}", mobile, e.fillInStackTrace());
        }
    }

}
