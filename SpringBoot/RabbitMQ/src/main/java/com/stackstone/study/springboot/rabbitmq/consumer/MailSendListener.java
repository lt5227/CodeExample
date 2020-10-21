package com.stackstone.study.springboot.rabbitmq.consumer;

import com.stackstone.study.springboot.rabbitmq.service.MailService;
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
public class MailSendListener {
    @Autowired
    private MailService mailService;

    @RabbitListener(queues = "${mq.mail.info.queue}", containerFactory = "multiListenerContainer")
    public void consumeLogInfo(@Payload String mailContent) {
        try {
            log.info("发送邮件消息监听： {}", mailContent);
            mailService.sendEmail(mailContent);
        } catch (Exception e) {
            log.error("发送邮件消息监听发生异常， {}", mailContent, e.fillInStackTrace());
        }
    }
}
