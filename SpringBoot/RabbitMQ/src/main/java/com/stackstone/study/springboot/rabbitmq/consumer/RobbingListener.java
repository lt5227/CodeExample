package com.stackstone.study.springboot.rabbitmq.consumer;

import com.stackstone.study.springboot.rabbitmq.service.ConcurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * RobbingListener
 * 抢单消息监听器
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 16:24
 * @since 1.0.0
 */
@Component
@Slf4j
public class RobbingListener {

    @Autowired
    private ConcurrencyService concurrencyService;

    @RabbitListener(queues = "${mq.robbing.info.queue}", containerFactory = "singleListenerContainerNotSetMessageConverter")
    public void consumeMessage(@Payload byte[] message) {
        try {
            String mobile = new String(message, StandardCharsets.UTF_8);
            log.info("监听到抢单的手机号： {}", mobile);
            // 进行抢单操作
            concurrencyService.manageRobbing(mobile);
        } catch (Exception e) {
            log.error("监听抢单消息 发生异常： ", e.fillInStackTrace());
        }
    }
}
