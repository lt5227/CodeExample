package com.stackstone.study.hystrix.demo;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;

import java.util.Collection;
import java.util.List;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * ProductQueryCollapser
 *
 * @author LiLei
 * @date 2021/8/24
 * @since 1.0.0
 */
public class ProductQueryCollapser extends HystrixCollapser<List<List<Product>>, List<Product>, String> {

    private final DemoServiceProvider provider;

    private final String key;

    public ProductQueryCollapser(String key, DemoServiceProvider provider) {
        super(
                Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("ProductQueryCollapser"))
                        .andScope(Scope.GLOBAL)
                        .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(200))
        );
        this.key = key;
        this.provider = provider;
    }

    @Override
    public String getRequestArgument() {
        System.out.println("getRequestArgument...");
        return key;
    }

    @Override
    protected HystrixCommand<List<List<Product>>> createCommand(Collection<CollapsedRequest<List<Product>, String>> collapsedRequests) {
        System.out.println("createCommand========>");
        return new ProductQueryCommand(provider, key, collapsedRequests);
    }

    @Override
    protected void mapResponseToRequests(List<List<Product>> batchResponse, Collection<CollapsedRequest<List<Product>, String>> collapsedRequests) {
        System.out.println("mapResponseToRequests...");
        int count = 0;
        for (CollapsedRequest<List<Product>, String> request : collapsedRequests) {
            request.setResponse(batchResponse.get(count++));
        }
    }
}
