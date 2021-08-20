package com.stackstone.study.hystrix.demo;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
    public int queryDocumentCount() throws InterruptedException, IOException {
        File file = new File("E:\\skywalking-api.log");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("123456");
        fileWriter.flush();
        Thread.sleep(6000L);
        fileWriter.write("456789");
        fileWriter.flush();
        fileWriter.close();
        System.out.println("DemoServiceProvider");
        return 1000;
    }
}
