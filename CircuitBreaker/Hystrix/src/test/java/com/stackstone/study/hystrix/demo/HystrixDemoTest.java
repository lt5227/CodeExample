package com.stackstone.study.hystrix.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * HystrixDemoTest
 *
 * @author LiLei
 * @date 2021/8/17
 * @since 1.0.0
 */
@SpringBootTest
@Slf4j
class HystrixDemoTest {
    @Autowired
    private DemoServiceProvider provider;

    @Test
    void testDemoCommand() throws InterruptedException {
        long start = System.currentTimeMillis();
        Integer integer = new DemoCommand(provider, null).execute();
        long end = System.currentTimeMillis();
        log.info("result: {}, time: {}", integer, (end - start));
        Thread.sleep(15000L);
        System.out.println("END");
    }
}
