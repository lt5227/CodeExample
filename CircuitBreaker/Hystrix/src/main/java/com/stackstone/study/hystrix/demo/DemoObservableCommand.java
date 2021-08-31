package com.stackstone.study.hystrix.demo;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;
import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * DemoObservableCommand
 *
 * @author LiLei
 * @date 2021/8/25
 * @since 1.0.0
 */
@Slf4j
public class DemoObservableCommand extends HystrixObservableCommand<Integer> {

    private final DemoServiceProvider demoServiceProvider;

    private final String key;

    public DemoObservableCommand(DemoServiceProvider provider, String key) {
        super(HystrixObservableCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("demoService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("queryDocumentCount2"))
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
                ));
        this.demoServiceProvider = provider;
        this.key = key;
    }

    @Override
    protected Observable<Integer> construct() {
        log.info(Thread.currentThread().getName() + " is running......");
        return Observable.unsafeCreate(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                log.info("construct call...");
                try {
                    if (subscriber.isUnsubscribed()) {
                        int result = demoServiceProvider.queryDocumentCount(key);
                        subscriber.onNext(result);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * fallback方法的写法，覆写resumeWithFallback方法
     * 当调用出现异常时，会调用该降级方法
     */
    @Override
    public Observable<Integer> resumeWithFallback() {
        return Observable.unsafeCreate(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> observer) {
                log.info("resumeWithFallback call...");
                try {
                    if (!observer.isUnsubscribed()) {
                        observer.onNext(-1);
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }
}
