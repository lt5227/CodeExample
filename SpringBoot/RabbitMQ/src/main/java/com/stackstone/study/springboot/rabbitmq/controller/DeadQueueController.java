package com.stackstone.study.springboot.rabbitmq.controller;

import com.stackstone.study.springboot.rabbitmq.core.BaseResponse;
import com.stackstone.study.springboot.rabbitmq.core.StatusCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * DeadQueueController
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 21:34
 * @since 1.0.0
 */
@RestController
@Slf4j
@Api(value = "死信队列控制层", tags = "死信队列控制层")
public class DeadQueueController {

    private static final String PREFIX = "dead/queue";

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment env;

    @GetMapping(PREFIX + "/send")
    @ApiOperation(value = "发送消息到死信队列", notes = "发送消息到死信队列")
    public BaseResponse<String> sendDeadQueueMsg() {
        BaseResponse<String> response = new BaseResponse<>(StatusCodeEnum.SUCCESS);
        try {
            rabbitTemplate.setExchange(env.getProperty("mq.deadQueue.simple.produce.exchange"));
            rabbitTemplate.setRoutingKey(Objects.requireNonNull(env.getProperty("mq.deadQueue.simple.produce.routingKey")));
            String str = "死信队列消息";
            rabbitTemplate.convertAndSend(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
