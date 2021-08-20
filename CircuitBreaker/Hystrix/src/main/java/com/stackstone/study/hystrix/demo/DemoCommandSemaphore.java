//package com.stackstone.study.hystrix.demo;
//
//import com.netflix.hystrix.HystrixCommand;
//import com.netflix.hystrix.HystrixCommandGroupKey;
//import com.netflix.hystrix.HystrixCommandKey;
//import com.netflix.hystrix.HystrixCommandProperties;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * Copyright 2021 PatSnap All rights reserved.
// * DemoCommandSemaphore
// *
// * @author LiLei
// * @date 2021/8/17
// * @since 1.0.0
// */
//@Slf4j
//public class DemoCommandSemaphore extends HystrixCommand<Integer> {
//
//    private DemoServiceProvider provider;
//
//    public DemoCommandSemaphore(DemoServiceProvider provider) {
//        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("orderService"))
//                .andCommandKey(HystrixCommandKey.Factory.asKey("queryByOrderId"))
//                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
//                        // 至少有10个请求，熔断器才进行错误率的计算
//                        .withCircuitBreakerRequestVolumeThreshold(10)
//                        // 熔断器中断请求5秒后会进入半打开状态,放部分流量过去重试
//                        .withCircuitBreakerSleepWindowInMilliseconds(5000)
//
//                        .withCircuitBreakerErrorThresholdPercentage(50)// 错误率达到50开启熔断保护
//                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
//                        .withExecutionIsolationSemaphoreMaxConcurrentRequests(10)));//最大并发请求量
//
//    }
//}
