package com.stackstone.study.hystrix.demo;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Observer;

import java.util.List;
import java.util.concurrent.ExecutionException;

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
    public String test(String key) throws InterruptedException {
        long start = System.currentTimeMillis();
        DemoCommand demoCommand = new DemoCommand(provider, key);
        Integer integer = demoCommand.execute();
        long end = System.currentTimeMillis();
        log.info("result: {}, time: {}", integer, (end - start));
//        Thread.sleep(15000L);
        log.info("END");
        return "END";
    }

    @GetMapping("/test1")
    public String test1(String key) throws InterruptedException {
        long start = System.currentTimeMillis();
        DemoObservableCommand command = new DemoObservableCommand(provider, key);
        final Integer[] result = new Integer[1];
        command.observe().subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                log.info("onCompleted...");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Integer integer) {
                result[0] = integer;
                log.info("onNext:{}", integer);
            }
        });
        long end = System.currentTimeMillis();
        log.info("result: {}, time: {}", result[0], (end - start));
        log.info("End...");
        return "END";
    }

    @GetMapping("/test2")
    public List<Product> test2(String sku) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            ProductQueryCollapser productQueryCollapser = new ProductQueryCollapser(sku, provider);
            List<Product> result = productQueryCollapser.queue().get();
            long end = System.currentTimeMillis();
            log.info("result:{}, time:{}", result, (end - start));
            return result;
        } finally {
            context.shutdown();
        }
    }
}
