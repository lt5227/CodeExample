package com.stackstone.study.springboot.quartz.tutorials.example7;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.DateBuilder.nextGivenSecondDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Copyright 2020 Oriental Standard All rights reserved.
 *
 * @author: LiLei
 * @className: InterruptExample
 * @createTime: 2020/10/15 10:47
 * @description: 中断的例子
 */
@Slf4j
public class InterruptExample {

    public void run() throws SchedulerException {
        log.info("------- Initializing ----------------------");
        // First we must get a reference to a scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        log.info("------- Initialization Complete -----------");

        log.info("------- Scheduling Jobs -------------------");
        // get a "nice round" time a few seconds in the future...
        Date startTime = DateBuilder.nextGivenSecondDate(null, 15);
        JobDetail job = JobBuilder.newJob(DumbInterruptableJob.class).withIdentity("interruptableJob1", "group1").build();
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();
        Date ft = scheduler.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");

        // start up the scheduler (jobs do not start to fire until the scheduler has been started)
        scheduler.start();
        log.info("------- Started Scheduler -----------------");

        log.info("------- Starting loop to interrupt job every 7 seconds ----------");
        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(7000L);
                // tell the scheduler to interrupt our job
                scheduler.interrupt(job.getKey());
            } catch (Exception e) {
                //
            }
        }

        log.info("------- Shutting Down ---------------------");
        scheduler.shutdown(true);
        log.info("------- Shutdown Complete -----------------");
        SchedulerMetaData metaData = scheduler.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

    public static void main(String[] args) throws Exception {
        InterruptExample example = new InterruptExample();
        example.run();
    }
}
