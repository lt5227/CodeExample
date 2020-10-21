package com.stackstone.study.springboot.rabbitmq.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackstone.study.springboot.rabbitmq.core.BaseResponse;
import com.stackstone.study.springboot.rabbitmq.core.StatusCodeEnum;
import com.stackstone.study.springboot.rabbitmq.core.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * AcknowledgeController
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 17:05
 * @since 1.0.0
 */
@RestController
@Api(value = "消息确认控制器", tags = "消息确认控制器")
@Slf4j
public class AcknowledgeController {
    private static final String PREFIX = "ack";

    @Autowired
    private Environment env;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(PREFIX + "/user/info")
    @ApiOperation(value = "消息确认样例", notes = "消息确认样例，用户ID为1抛出异常，消息保留不确认")
    public BaseResponse<String> ackUser(@RequestBody UserDTO userDTO) {
        BaseResponse<String> result = null;
        try {
            result = new BaseResponse<>(StatusCodeEnum.SUCCESS);
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("mq.ackConcurrent.info.exchange"));
            rabbitTemplate.setRoutingKey(Objects.requireNonNull(env.getProperty("mq.ackConcurrent.info.routingKey")));
            Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(userDTO))
                    // 消息持久化
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
            rabbitTemplate.convertAndSend(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
