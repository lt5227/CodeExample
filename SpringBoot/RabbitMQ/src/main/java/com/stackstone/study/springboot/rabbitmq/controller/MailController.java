package com.stackstone.study.springboot.rabbitmq.controller;

import com.stackstone.study.springboot.rabbitmq.core.BaseResponse;
import com.stackstone.study.springboot.rabbitmq.core.StatusCodeEnum;
import com.stackstone.study.springboot.rabbitmq.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * MailController
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020 /10/21 20:19
 * @since 1.0.0
 */
@RestController
@Slf4j
@Api(value = "邮件发送控制层", tags = "邮件发送控制层")
public class MailController {

    private static final String PREFIX = "mail";
    @Autowired
    private MailService mailService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment env;

    /**
     * Spring Boot + Java Mail 发送邮件
     * Send mail base response.
     *
     * @return the base response
     */
    @GetMapping(PREFIX + "/send")
    @ApiOperation(value = "发送邮件测试接口(同步执行)", notes = "发送邮件测试接口(同步执行)")
    public BaseResponse<String> sendMail() {
        BaseResponse<String> response = new BaseResponse<>(StatusCodeEnum.SUCCESS);
        try {
            String content = "<h1>发送邮件测试</h1>";
            mailService.sendEmail(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Send mail asynchronous base response.
     *
     * @param content the content
     * @return the base response
     */
    @GetMapping(PREFIX + "/send/asynchronous")
    @ApiOperation(value = "发送邮件测试接口(消息队列 异步执行)", notes = "发送邮件测试接口(消息队列 异步执行)")
    public BaseResponse<String> sendMailAsynchronous(String content) {
        BaseResponse<String> response = new BaseResponse<>(StatusCodeEnum.SUCCESS);
        try {
            rabbitTemplate.setExchange(env.getProperty("mq.mail.info.exchange"));
            rabbitTemplate.setRoutingKey(Objects.requireNonNull(env.getProperty("mq.mail.info.routingKey")));
            rabbitTemplate.convertAndSend(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
