package com.stackstone.study.springboot.rabbitmq.consumer;

import com.stackstone.study.springboot.rabbitmq.core.dto.LogDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * LogSystemListener
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 19:16
 * @since 1.0.0
 */
@Component
@Slf4j
public class LogSystemListener {

    @RabbitListener(queues = "${mq.log.info.queue}", containerFactory = "multiListenerContainer")
    public void consumeLogInfo(@Payload LogDTO logDTO) {
        try {
            log.info("系统日志监听器监听到消息： {}", logDTO);
        } catch (Exception e) {
            log.error("系统日志监听器监听发生异常， {}", logDTO, e.fillInStackTrace());
        }
    }
}
