package com.stackstone.study.springboot.quartz.tutorials.example1;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * Copyright 2020 Oriental Standard All rights reserved.
 *
 * @author: LiLei
 * @className: SimpleExample
 * @createTime: 2020/10/14 14:52
 * @description: This Example will demonstrate how to start and shutdown the Quartz scheduler and how to
 * schedule a job to run in Quartz.
 */
@Slf4j
public class SimpleExample {
    public void run() throws SchedulerException {
        log.info("------- Initializing ----------------------");
        // First we must get a reference to a scheduler
        // 首先，我们必须获得一个对调度程序的引用
        SchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        log.info("------- Initialization Complete -----------");

        // computer a time that is on he next round minute
        // 计算下一轮的时间(当前时间的下一分钟)
        Date runTime = DateBuilder.evenMinuteDate(new Date());
        log.info("------- Scheduling Job  -------------------");

        // define the job and tie it to our HelloJob class
        // 定义工作并将其绑定到HelloJob类
        JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job1", "group1").build();

        // Trigger the job to run on the next round minute
        // 触发该作业在下一轮分钟内运行
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();

        // Tell quartz to schedule the job using our trigger
        // 告诉quartz使用我们的触发器调度作业
        scheduler.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + runTime);

        // Start up the scheduler (nothing can actually run until the scheduler has been started)
        // 启动调度程序(在调度程序启动之前，实际上什么都不能运行)
        scheduler.start();
        log.info("------- Started Scheduler -----------------");

        // wait long enough so that the scheduler as an opportunity to run the job!
        // 等待足够长的时间，以便调度器有机会运行作业!
        log.info("------- Waiting 15 seconds... -------------");
        try {
            // wait 65 seconds to show job
            Thread.sleep(65 * 1000L);
            //
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        // shut down the scheduler
        // 关闭调度程序
        log.info("------- Shutting Down ---------------------");
        scheduler.shutdown(true);
        log.info("------- Shutdown Complete -----------------");
    }

    public static void main(String[] args) throws SchedulerException {
        SimpleExample example = new SimpleExample();
        example.run();
    }
}
