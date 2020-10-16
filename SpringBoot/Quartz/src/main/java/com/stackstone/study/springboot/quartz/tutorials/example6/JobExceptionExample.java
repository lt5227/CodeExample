package com.stackstone.study.springboot.quartz.tutorials.example6;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Copyright 2020 Oriental Standard All rights reserved.
 *
 * @author: LiLei
 * @className: JobExceptionExample
 * @createTime: 2020/10/15 10:27
 * @description: This job demonstrates how Quartz can handle JobExecutionExceptions that are thrown by jobs.
 */
@Slf4j
public class JobExceptionExample {
    public void run() throws SchedulerException {
        log.info("------- Initializing ----------------------");
        // First we must get a reference to a scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        log.info("------- Initialization Complete ------------");

        log.info("------- Scheduling Jobs -------------------");
        // jobs can be scheduled before start() has been called
        // get a "nice round" time a few seconds in the future...
        Date startTime = DateBuilder.nextGivenSecondDate(null, 15);

        // badJob1 will run every 10 seconds this job will throw an exception and refire immediately
        JobDetail job = JobBuilder.newJob(BadJob1.class).withIdentity("badJob1", "group1")
                .usingJobData("denominator", "0").build();
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();
        Date ft = scheduler.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");


        // badJob2 will run every five seconds this job will throw an exception and never refire
        job = newJob(BadJob2.class).withIdentity("badJob2", "group1").build();
        trigger = newTrigger().withIdentity("trigger2", "group1").startAt(startTime)
                .withSchedule(simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();
        ft = scheduler.scheduleJob(job, trigger);
        log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
                + trigger.getRepeatInterval() / 1000 + " seconds");


        log.info("------- Starting Scheduler ----------------");
        // jobs don't start firing until start() has been called...
        scheduler.start();
        log.info("------- Started Scheduler -----------------");

        try {
            // sleep for 30 seconds
            Thread.sleep(30L * 1000L);
        } catch (Exception e) {
            //
        }

        log.info("------- Shutting Down ---------------------");
        scheduler.shutdown(false);
        log.info("------- Shutdown Complete -----------------");
        SchedulerMetaData metaData = scheduler.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

    public static void main(String[] args) throws Exception {
        JobExceptionExample example = new JobExceptionExample();
        example.run();
    }
}
