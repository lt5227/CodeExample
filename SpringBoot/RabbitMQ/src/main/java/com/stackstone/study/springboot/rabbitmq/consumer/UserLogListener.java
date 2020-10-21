package com.stackstone.study.springboot.rabbitmq.consumer;

import com.stackstone.study.springboot.rabbitmq.db.mysql.entity.UserLogEntity;
import com.stackstone.study.springboot.rabbitmq.db.mysql.repository.UserLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserLogListener {
    @Autowired
    private UserLogRepository userLogRepository;

    @RabbitListener(queues = "${mq.userLog.info.queue}", containerFactory = "multiListenerContainer")
    public void consumeLogInfo(@Payload UserLogEntity userLogEntity) {
        try {
            log.info("用户日志监听器监听到消息： {}", userLogEntity);
            userLogRepository.save(userLogEntity);
        } catch (Exception e) {
            log.error("用户日志监听器监听发生异常， {}", userLogEntity, e.fillInStackTrace());
        }
    }
}
