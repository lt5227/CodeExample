package com.stackstone.study.hystrix.demo;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class DemoServiceProvider {
    FileWriter fileWriter = null;

    public void close() {
        log.info("关闭资源");
        if (fileWriter != null) {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int queryDocumentCount(String a) throws InterruptedException, IOException {
        log.info("queryDocumentCount...");
        File file = new File("E:\\skywalking-api.log");
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write("123456");
            fileWriter.flush();
//            if (!"1".equals(a)) {
//                Thread.sleep(60000L);
//            }
            long i = Integer.MAX_VALUE;
            while (true) {
                if (i-- == Integer.MIN_VALUE) {
                    log.info("Demo....");
                    break;
                }
            }
            log.info("测试程序运行结束11111");
            fileWriter.write("456789");
            fileWriter.flush();
            log.info("测试程序运行结束22222");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            log.info("finally....");
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
        log.info("DemoServiceProvider...");
        return 1000;
    }

    public List<Product> queryProducts(String sku) {
        log.info("queryProducts....");
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
