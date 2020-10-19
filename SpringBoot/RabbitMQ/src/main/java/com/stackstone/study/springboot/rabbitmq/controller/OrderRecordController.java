package com.stackstone.study.springboot.rabbitmq.controller;

import com.stackstone.study.springboot.rabbitmq.core.BaseResponse;
import com.stackstone.study.springboot.rabbitmq.core.StatusCodeEnum;
import com.stackstone.study.springboot.rabbitmq.listener.event.PushOrderRecordEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OrderRecordController.java
 *
 * <p>Copyright (c) 2020 StackStone All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020-10-19 17:21:59
 * @since 1.0.0
 */
@RestController
@Slf4j
@Api(value = "订单记录控制层", tags = "订单记录控制层(Spring 事件驱动模型演示)")
public class OrderRecordController {

    private static final String PREFIX = "order";

    /**
     * 类似于RabbitTemplate
     */
    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping(value = PREFIX + "/push")
    @ApiOperation(value = "推送订单", notes = "推送订单，演示Spring事件驱动模型")
    public BaseResponse<String> pushOrder() {
        BaseResponse<String> response = new BaseResponse<>(StatusCodeEnum.SUCCESS);
        PushOrderRecordEvent event = new PushOrderRecordEvent(this, "orderNo_20201019", "物流12");
        publisher.publishEvent(event);
        return response;
    }
}
