package com.stackstone.study.hystrix.demo;

import com.netflix.hystrix.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * ProductQueryCommand
 *
 * @author LiLei
 * @date 2021/8/24
 * @since 1.0.0
 */
public class ProductQueryCommand extends HystrixCommand<List<List<Product>>> {

    private final DemoServiceProvider provider;

    private final String queryKey;

    private final Collection<HystrixCollapser.CollapsedRequest<List<Product>, String>> requests;

    protected ProductQueryCommand(DemoServiceProvider provider,
                                  String queryKey,
                                  Collection<HystrixCollapser.CollapsedRequest<List<Product>, String>> requests) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("queryProducts"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.defaultSetter()
                                .withCircuitBreakerEnabled(false)
                                // 至少有10个请求，熔断才进行错误率的计算
                                .withCircuitBreakerRequestVolumeThreshold(2)
                                // 熔断器中断请求5秒后会进入半打开状态，放部分流量过去重试
                                .withCircuitBreakerSleepWindowInMilliseconds(60000)
                                // 错误率达到50开启熔断保护
                                .withCircuitBreakerErrorThresholdPercentage(50)
                                .withExecutionTimeoutEnabled(true)
                                // 执行超时时间
                                .withExecutionTimeoutInMilliseconds(65000)
                )
//                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withMaximumSize(50))
//                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(20))
        );
        this.provider = provider;
        this.queryKey = queryKey;
        this.requests = requests;
        System.out.println("ProductQueryCommand...");
    }


    @Override
    protected List<List<Product>> run() throws Exception {
        System.out.println("run....");
        List<List<Product>> result = new ArrayList<>();
        for (HystrixCollapser.CollapsedRequest<List<Product>, String> request : requests) {
            String queryKey = request.getArgument();
            System.out.println(queryKey);
            List<Product> products = provider.queryProducts(queryKey);
            result.add(products);
        }
        return result;
    }
}
