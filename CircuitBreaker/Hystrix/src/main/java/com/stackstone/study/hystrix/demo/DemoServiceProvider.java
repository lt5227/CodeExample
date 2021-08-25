package com.stackstone.study.hystrix.demo;

import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * DemoServiceProvider
 *
 * @author LiLei
 * @date 2021/8/17
 * @since 1.0.0
 */
@Service
public class DemoServiceProvider {
    public int queryDocumentCount(String a) throws InterruptedException, IOException {
        System.out.println("queryDocumentCount...");
        File file = new File("E:\\skywalking-api.log");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("123456");
        fileWriter.flush();
        if (!"1".equals(a)) {
            Thread.sleep(1000L);
        }
        fileWriter.write("456789");
        fileWriter.flush();
        fileWriter.close();
        System.out.println("DemoServiceProvider");
        return 1000;
    }

    public List<Product> queryProducts(String sku) {
        System.out.println("queryProducts....");
        List<Product> products = new ArrayList<>();
        if ("0".equals(sku)) {
            Product product = new Product();
            product.setProductId(0L);
            product.setPrice(new BigDecimal("0"));
            product.setSku("Demo0");
            products.add(product);
            return products;
        }
        if ("1".equals(sku)) {
            Product product = new Product();
            product.setProductId(1L);
            product.setPrice(new BigDecimal("1"));
            product.setSku("Demo1");
            products.add(product);
            return products;
        }
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setProductId((long) i);
            product.setPrice(new BigDecimal("222"));
            product.setSku("Demo" + i);
            products.add(product);
        }
        return products;
    }
}
