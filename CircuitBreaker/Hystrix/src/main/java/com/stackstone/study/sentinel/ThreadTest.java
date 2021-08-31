package com.stackstone.study.sentinel;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class ThreadTest {
    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                long a = System.currentTimeMillis();
                for (int i = 0; i < 200000000; i++) {
                    if (Thread.interrupted()) {
                        return;
                    }
                    log.info("循环输出， {}", i);
                }
                long b = System.currentTimeMillis();
                System.out.println(b - a);
            }
        };
        System.out.println();
        thread.start();
    }
}
