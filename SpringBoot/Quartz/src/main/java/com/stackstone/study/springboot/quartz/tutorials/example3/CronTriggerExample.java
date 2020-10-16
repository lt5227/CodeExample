package com.stackstone.study.springboot.quartz.tutorials.example3;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * Copyright 2020 Oriental Standard All rights reserved.
 *
 * @author: LiLei
 * @className: CronTriggerExample
 * @createTime: 2020/10/14 16:10
 * @description: This Example will demonstrate all of the basics of scheduling capabilities of Quartz using Cron Triggers.
 */
@Slf4j
public class CronTriggerExample {

    public void run() throws SchedulerException {
        log.info("------- Initializing -------------------");
        // First we must get a reference to a scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        log.info("------- Initialization Complete --------");

        log.info("------- Scheduling Jobs ----------------");
        // jobs can be scheduled before sched.start() has been called

        // job 1 will run every 20 seconds
        JobDetail job = JobBuilder.newJob(SimpleJob.class).withIdentity("job1", "group1").build();
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
        Date ft = scheduler.scheduleJob(job, trigger);
        log.info("{} has been scheduled to run at: {} and repeat based on expression: {}", job.getKey(),
                ft, trigger.getCronExpression());


        // job 2 will run every other minute (at 15 seconds past the minute)
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job2", "group1").build();
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("15 0/2 * * * ?")).build();
        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} has been scheduled to run at: {} and repeat based on expression: {}", job.getKey(),
                ft, trigger.getCronExpression());


        // job 3 will run every other minute but only between 8am and 5pm
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job3", "group1").build();
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger3", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 8-17 * * ?")).build();
        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} has been scheduled to run at: {} and repeat based on expression: {}", job.getKey(),
                ft, trigger.getCronExpression());


        // job 4 will run every three minutes but only between 5pm and 11pm
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job4", "group1").build();
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger4", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/3 17-23 * * ?")).build();
        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} has been scheduled to run at: {} and repeat based on expression: {}", job.getKey(),
                ft, trigger.getCronExpression());


        // job 5 will run at 10am on the 1st and 15th days of the month
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job5", "group1").build();
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger5", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 10am 1,15 * ?")).build();
        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} has been scheduled to run at: {} and repeat based on expression: {}", job.getKey(),
                ft, trigger.getCronExpression());


        // job 6 will run every 30 seconds but only on Weekdays (Monday through Friday)
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job6", "group1").build();
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger6", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0,30 * * ? * MON-FRI")).build();
        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} has been scheduled to run at: {} and repeat based on expression: {}", job.getKey(),
                ft, trigger.getCronExpression());


        // job 7 will run every 30 seconds but only on Weekends (Saturday and Sunday)
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job7", "group1").build();
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger7", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0,30 * * ? * SAT,SUN")).build();
        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} has been scheduled to run at: {} and repeat based on expression: {}", job.getKey(),
                ft, trigger.getCronExpression());


        log.info("------- Starting Scheduler ----------------");
        /*
            All of the jobs have been added to the scheduler,
            but none of the jobs will run until the scheduler has been started
         */
        scheduler.start();
        log.info("------- Started Scheduler -----------------");

        log.info("------- Waiting five minutes... ------------");
        try {
            // wait five minutes to show jobs
            Thread.sleep(300L * 1000L);
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
        CronTriggerExample example = new CronTriggerExample();
        example.run();
    }
}
