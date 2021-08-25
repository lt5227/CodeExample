package com.stackstone.study.hystrix.demo;

import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;

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

    private final String a;

    public DemoCommand(DemoServiceProvider provider, String a) {
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
                                .withExecutionTimeoutInMilliseconds(5000)
                )
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(2)));
        this.demoServiceProvider = provider;
        this.a = a;
    }

    @Override
    protected Integer run() throws Exception {
        return demoServiceProvider.queryDocumentCount(a);
    }

    @Override
    protected Integer getFallback() {
        return -1;
    }
}
