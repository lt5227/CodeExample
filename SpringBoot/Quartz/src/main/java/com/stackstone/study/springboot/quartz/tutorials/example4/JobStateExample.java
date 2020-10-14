package com.stackstone.study.springboot.quartz.tutorials.example4;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;


/**
 * Copyright 2020 Oriental Standard All rights reserved.
 *
 * @author: LiLei
 * @className: JobStateExample
 * @createTime: 2020/10/14 16:57
 * @description: This Example will demonstrate how job parameters can be passed into jobs and how state can be maintained
 */
@Slf4j
public class JobStateExample {

    public void run() throws SchedulerException {
        log.info("------- Initializing -------------------");
        // First we must get a reference to a scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        log.info("------- Initialization Complete --------");

        log.info("------- Scheduling Jobs ----------------");
        // get a "nice round" time a few seconds in the future....
        Date startTime = DateBuilder.nextGivenSecondDate(null, 10);

        // job1 will only run 5 times (at start time, plus 4 repeats), every 10 seconds
        JobDetail job1 = JobBuilder.newJob(ColorJob.class).withIdentity("job1", "group1").build();
        SimpleTrigger trigger1 = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(4)).build();
        // pass initialization parameters into the job
        job1.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "Green");
        job1.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);
        // schedule the job to run
        Date scheduleTime1 = scheduler.scheduleJob(job1, trigger1);
        log.info(job1.getKey() + " will run at: " + scheduleTime1 + " and repeat: " + trigger1.getRepeatCount()
                + " times, every " + trigger1.getRepeatInterval() / 1000 + " seconds");


        // job2 will also run 5 times, every 10 seconds
        JobDetail job2 = JobBuilder.newJob(ColorJob.class).withIdentity("job2", "group1").build();

        SimpleTrigger trigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1").startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(4)).build();
        // pass initialization parameters into the job
        // this job has a different favorite color!
        job2.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "Red");
        job2.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);
        // schedule the job to run
        Date scheduleTime2 = scheduler.scheduleJob(job2, trigger2);
        log.info(job2.getKey().toString() + " will run at: " + scheduleTime2 + " and repeat: " + trigger2.getRepeatCount()
                + " times, every " + trigger2.getRepeatInterval() / 1000 + " seconds");

        log.info("------- Starting Scheduler ----------------");
        // All of the jobs have been added to the scheduler, but none of the jobs
        // will run until the scheduler has been started
        scheduler.start();
        log.info("------- Started Scheduler -----------------");
        log.info("------- Waiting 60 seconds... -------------");
        try {
            // wait five minutes to show jobs
            Thread.sleep(60L * 1000L);
            // executing...
        } catch (Exception e) {
            //
        }

        log.info("------- Shutting Down ---------------------");
        scheduler.shutdown(true);
        log.info("------- Shutdown Complete -----------------");

        SchedulerMetaData metaData = scheduler.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

    public static void main(String[] args) throws Exception {
        JobStateExample example = new JobStateExample();
        example.run();
    }
}
