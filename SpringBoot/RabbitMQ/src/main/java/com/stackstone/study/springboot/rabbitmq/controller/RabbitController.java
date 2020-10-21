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
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * RabbitController
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020 /10/21 10:43
 * @since 1.0.0
 */
@RestController
@Slf4j
@Api(value = "RabbitMQ基础消息控制层", tags = "RabbitMQ基础消息控制层")
public class RabbitController {

    private static final String PREFIX = "rabbitmq";

    /**
     * RabbitTemplate默认注入的是Spring所定义的
     * 我们可以在RabbitMqConfig1 中对 RabbitTemplate重新进行定义
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment env;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 发送简单消息
     * Send simple message base response.
     *
     * @param message the message
     * @return the base response
     */
    @GetMapping(PREFIX + "/simple/message/send")
    @ApiOperation(value = "发送简单消息", notes = "发送简单消息")
    public BaseResponse<String> sendSimpleMessage(@RequestParam String message) {
        BaseResponse<String> response = new BaseResponse<>(StatusCodeEnum.SUCCESS);
        try {
            log.info("待发送的消息：{}", message);
            // 设置消息转换器
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            // 设置交换机
            rabbitTemplate.setExchange(env.getProperty("mq.basic.info.exchange"));
            // 设置RoutingKey
            rabbitTemplate.setRoutingKey(Objects.requireNonNull(env.getProperty("mq.basic.info.routingKey")));
            /* 发送消息 */
            // 发送字符串
            Message msg1 = MessageBuilder.withBody(objectMapper.writeValueAsBytes(message)).build();
            rabbitTemplate.send(msg1);
            // 发送json对象
            // 设置消息转换
            Map<String, Object> map = Collections.singletonMap("test", "测试");
            Message msg2 = MessageBuilder.withBody(objectMapper.writeValueAsBytes(map)).build();
            rabbitTemplate.convertAndSend(msg2);
        } catch (Exception e) {
            log.error("发送简单消息发送异常：", e.fillInStackTrace());
        }
        return response;
    }


    /**
     * 发送对象消息
     * Send object message base response.
     *
     * @param userDTO the user
     * @return the base response
     */
    @PostMapping(PREFIX + "/object/message/send")
    @ApiOperation(value = "发送对象消息", notes = "发送对象消息")
    public BaseResponse<String> sendObjectMessage(@RequestBody UserDTO userDTO) {
        BaseResponse<String> response = new BaseResponse<>(StatusCodeEnum.SUCCESS);
        try {
            log.info("待发送的消息：{}", userDTO);
            // 设置交换机
            rabbitTemplate.setExchange(env.getProperty("mq.basic.info.exchange"));
            // 设置RoutingKey
            rabbitTemplate.setRoutingKey(Objects.requireNonNull(env.getProperty("mq.basic.info.routingKey")));
            // 设置消息转换
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            Message msg = MessageBuilder.withBody(objectMapper.writeValueAsBytes(userDTO))
                    .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT)
                    .build();
            rabbitTemplate.convertAndSend(msg);
        } catch (Exception e) {
            log.error("发送简单消息发送异常：", e.fillInStackTrace());
        }
        return response;
    }
}
