package com.stackstone.study.springboot.rabbitmq.controller;

import com.stackstone.study.springboot.rabbitmq.core.BaseResponse;
import com.stackstone.study.springboot.rabbitmq.core.StatusCodeEnum;
import com.stackstone.study.springboot.rabbitmq.service.InitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ConcurrencyController
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020 /10/21 15:28
 * @since 1.0.0
 */
@RestController
@Api(value = "并发控制器", tags = "并发控制器")
@Slf4j
public class ConcurrencyController {
    private static final String PREFIX = "concurrency";

    @Autowired
    private InitService initService;

    /**
     * 直接访问数据库进行操作抢单
     * Robbing thread 01 base response.
     *
     * @return the base response
     */
    @GetMapping(PREFIX + "/robbing/thread")
    @ApiOperation(value = "并发线程开启（数据库）", notes = "并发线程开启（数据库）")
    public BaseResponse<String> robbingThread01() {
        BaseResponse<String> response = new BaseResponse<>(StatusCodeEnum.SUCCESS);
        initService.generateMultiThread();
        return response;
    }

    /**
     * 架设消息中间件进行消峰，用队列控制抢单
     * Robbing thread 02 base response.
     *
     * @return the base response
     */
    @GetMapping(PREFIX + "/robbing/thread/mq")
    @ApiOperation(value = "并发线程开启（消息队列）", notes = "并发线程开启（消息队列）")
    public BaseResponse<String> robbingThread02() {
        BaseResponse<String> response = new BaseResponse<>(StatusCodeEnum.SUCCESS);
        initService.generateMultiThreadByMq();
        return response;
    }
}
