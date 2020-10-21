package com.stackstone.study.springboot.rabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.CountDownLatch;

/**
 * InitService
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 15:16
 * @since 1.0.0
 */
@Service
@Slf4j
public class InitService {
    private static final int THREAD_NUM = 500;

    private static int mobile = 0;

    @Autowired
    private ConcurrencyService concurrencyService;
    @Autowired
    private CommonMqService commonMqService;


    public void generateMultiThread() {
        log.info("开始初始化线程数----->");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(new RunThread01(countDownLatch)).start();
        }
        // 启动多个线程
        countDownLatch.countDown();
    }

    public void generateMultiThreadByMq() {
        log.info("开始初始化线程数----->");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < THREAD_NUM; i++) {
            new Thread(new RunThread02(countDownLatch)).start();
        }
        // 启动多个线程
        countDownLatch.countDown();
    }

    /**
     * 运行线程1 直接操作数据库
     */
    private class RunThread01 implements Runnable {
        private final CountDownLatch startLatch;

        public RunThread01(CountDownLatch startLatch) {
            this.startLatch = startLatch;
        }

        @Override
        public void run() {
            try {
                // 线程等待(利用CountDownLatch 初始所有线程都会卡在这里处于等待状态，当countDownLatch.countDown()运行后
                // 所有线程会在同一时间运行后面的代码，模拟高并发同时访问的情况)
                startLatch.await();
                mobile += 1;
                log.info("当前手机号：{}", mobile);
                // 抢单
                concurrencyService.manageRobbing(String.valueOf(mobile));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 运行线程2 利用消息队列对请求排队处理
     */
    private class RunThread02 implements Runnable {
        private final CountDownLatch startLatch;

        public RunThread02(CountDownLatch startLatch) {
            this.startLatch = startLatch;
        }

        @Override
        public void run() {
            try {
                // 线程等待(利用CountDownLatch 初始所有线程都会卡在这里处于等待状态，当countDownLatch.countDown()运行后
                // 所有线程会在同一时间运行后面的代码，模拟高并发同时访问的情况)
                startLatch.await();
                mobile += 1;
                log.info("当前手机号：{}", mobile);
                commonMqService.sendRobbingMsg(String.valueOf(mobile));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
