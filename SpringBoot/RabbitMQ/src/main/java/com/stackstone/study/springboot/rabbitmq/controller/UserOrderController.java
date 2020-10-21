package com.stackstone.study.springboot.rabbitmq.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackstone.study.springboot.rabbitmq.core.BaseResponse;
import com.stackstone.study.springboot.rabbitmq.core.StatusCodeEnum;
import com.stackstone.study.springboot.rabbitmq.core.dto.LogDTO;
import com.stackstone.study.springboot.rabbitmq.core.dto.UserOrderDTO;
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
 * UserOrderController
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 17:40
 * @since 1.0.0
 */
@RestController
@Slf4j
@Api(value = "用户下单控制层(消息队列实战)", tags = "用户下单控制层(消息队列实战)")
public class UserOrderController {

    private static final String PREFIX = "user/order";

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Environment env;

    @PostMapping(PREFIX + "/push")
    @ApiOperation(value = "推送用户订单", notes = "推送用户订单")
    public BaseResponse<String> pushUserOrder(@RequestBody UserOrderDTO userOrderDTO) {
        BaseResponse<String> response = new BaseResponse<>(StatusCodeEnum.SUCCESS);
        try {
            log.debug("接收到数据:{}", userOrderDTO);
            // 用户下单记录-入库
            rabbitTemplate.setExchange(env.getProperty("mq.userOrder.info.exchange"));
            rabbitTemplate.setRoutingKey(Objects.requireNonNull(env.getProperty("mq.userOrder.info.routingKey")));
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(userOrderDTO))
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .build();
            rabbitTemplate.convertAndSend(message);

            // 系统级别-日志记录 异步记录
            LogDTO logDTO = new LogDTO();
            logDTO.setMethodName("用户下单");
            logDTO.setOperateData(userOrderDTO.toString());
            rabbitTemplate.setExchange(env.getProperty("mq.log.info.exchange"));
            rabbitTemplate.setRoutingKey(Objects.requireNonNull(env.getProperty("mq.log.info.routingKey")));
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.convertAndSend(logDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
