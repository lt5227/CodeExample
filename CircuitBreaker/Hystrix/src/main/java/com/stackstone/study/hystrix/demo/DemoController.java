package com.stackstone.study.hystrix.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * DemoController
 *
 * @author LiLei
 * @date 2021/8/17
 * @since 1.0.0
 */
@RestController
@Slf4j
public class DemoController {

    @Autowired
    private DemoServiceProvider provider;

    @GetMapping("/test")
    public String test() throws InterruptedException {
        long start = System.currentTimeMillis();
        Integer integer = new DemoCommand(provider).execute();
        long end = System.currentTimeMillis();
        log.info("result: {}, time: {}", integer, (end - start));
        Thread.sleep(15000L);
        System.out.println("END");
        return "END";
    }
}
