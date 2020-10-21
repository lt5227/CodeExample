package com.stackstone.study.springboot.rabbitmq.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackstone.study.springboot.rabbitmq.core.BaseResponse;
import com.stackstone.study.springboot.rabbitmq.core.StatusCodeEnum;
import com.stackstone.study.springboot.rabbitmq.db.mysql.entity.UserEntity;
import com.stackstone.study.springboot.rabbitmq.db.mysql.entity.UserLogEntity;
import com.stackstone.study.springboot.rabbitmq.db.mysql.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * UserController
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 19:46
 * @since 1.0.0
 */
@RestController
@Slf4j
@Api(value = "模拟用户登录控制层", tags = "模拟用户登录控制层")
public class UserController {

    private static final String PREFIX = "user";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment env;

    @PostMapping(PREFIX + "/login")
    @ApiOperation(value = "模拟用户登录(异步记录日志)", notes = "模拟用户登录(异步记录日志)")
    public BaseResponse<String> login(String userName, String password) {
        BaseResponse<String> response = new BaseResponse<>(StatusCodeEnum.SUCCESS);
        try {
            UserEntity userEntity = userRepository.findByUserNameAndPassword(userName, password);
            if (userEntity != null) {
                // 异步写用户日志
                UserLogEntity userLogEntity = new UserLogEntity(userName, "Login",
                        "login", objectMapper.writeValueAsString(userEntity));
                // 发送用户登录日志到消息队列
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(env.getProperty("mq.userLog.info.exchange"));
                rabbitTemplate.setRoutingKey(Objects.requireNonNull(env.getProperty("mq.userLog.info.routingKey")));
                rabbitTemplate.convertAndSend(userLogEntity);
            } else {
                response = new BaseResponse<>(StatusCodeEnum.FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
