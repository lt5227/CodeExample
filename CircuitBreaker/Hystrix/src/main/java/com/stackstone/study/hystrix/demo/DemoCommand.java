package com.stackstone.study.hystrix.demo;

import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.functions.Action0;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * DemoCommand
 *
 * @author LiLei
 * @date 2021/8/17
 * @since 1.0.0
 */
@Slf4j
public class DemoCommand extends HystrixCommand<Integer> {

    private final DemoServiceProvider demoServiceProvider;

    private final String key;

    public DemoCommand(DemoServiceProvider provider, String key) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("demoService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("queryDocumentCount"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.defaultSetter()
                                .withCircuitBreakerEnabled(false)
                                // 至少有10个请求，熔断才进行错误率的计算
                                .withCircuitBreakerRequestVolumeThreshold(2)
                                // 熔断器中断请求5秒后会进入半打开状态，放部分流量过去重试
                                .withCircuitBreakerSleepWindowInMilliseconds(5000)
                                // 错误率达到50开启熔断保护
                                .withCircuitBreakerErrorThresholdPercentage(50)
                                .withExecutionTimeoutEnabled(true)
                                // 执行超时时间
                                .withExecutionTimeoutInMilliseconds(1000)
                )
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(2)));
        this.demoServiceProvider = provider;
        this.key = key;
    }

    @Override
    protected Integer run() throws Exception {
        int result;
        try {
            result = demoServiceProvider.queryDocumentCount(key);
        } finally {
            demoServiceProvider.close();
        }
        return result;
    }

    @Override
    protected Integer getFallback() {
        log.info("熔断");
        return -1;
    }

    @Override
    public Observable<Integer> observe() {
        return super.observe().doOnTerminate(new Action0() {
            @Override
            public void call() {
                log.info("456");
            }
        });
    }

    @Override
    public Observable<Integer> toObservable() {
        return super.toObservable().doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        log.info("12345678");
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        log.info("789");
                    }
                })
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        log.info("123");
                    }
                });
    }
}
