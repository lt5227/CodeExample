package com.stackstone.study.springboot.quartz.tutorials.example2;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * Copyright 2020 Oriental Standard All rights reserved.
 *
 * @author: LiLei
 * @className: SimpleTriggerExample
 * @createTime: 2020/10/14 15:18
 * @description: This Example will demonstrate all of the basics of scheduling
 * capabilities of Quartz using Simple Triggers.
 */
@Slf4j
public class SimpleTriggerExample {
    public void run() throws SchedulerException {
        log.info("------- Initializing -------------------");
        // First we must get a reference to a scheduler
        // 首先，我们必须获得一个对调度程序的引用
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        log.info("------- Initialization Complete --------");

        log.info("------- Scheduling Jobs ----------------");
        // jobs can be scheduled before scheduler.start() has been called
        // 可以在调用scheduler.start()之前调度作业

        // get a "nice round" time a few seconds in the future...
        Date startTime = DateBuilder.nextGivenSecondDate(null, 15);

        // job1 will only fire once at date/time "ts"
        JobDetail job = JobBuilder.newJob(SimpleJob.class).withIdentity("job1", "group1").build();

        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1").startAt(startTime).build();

        // schedule it to run!
        Date ft = scheduler.scheduleJob(job, trigger);
        log.info("{} will run at:{} and repeat:{}, times, every {} seconds", job.getKey(), ft,
                trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);

        // job2 will only fire once at date/time "ts"
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job2", "group1").build();
        trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1").startAt(startTime).build();
        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} will run at:{} and repeat:{}, times, every {} seconds", job.getKey(), ft,
                trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);

        // job3 will run 11 times (run once and repeat 10 more times) job3 will repeat every 10 seconds
        // job3将运行11次(运行一次，重复10次)，job3将每10秒重复一次
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job3", "group1").build();
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10);
        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger3", "group1").startAt(startTime).withSchedule(builder).build();
        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} will run at:{} and repeat:{}, times, every {} seconds", job.getKey(), ft,
                trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);

        // the same job (job3) will be scheduled by a another trigger this time will only repeat twice at a 70 second interval
        // 同一个作业(job3)将由另一个触发器调度，这一次将以70秒的间隔重复两次
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger3", "group2").startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(2))
                .forJob(job).build();
        ft = scheduler.scheduleJob(trigger);
        log.info("{} will run at:{} and repeat:{}, times, every {} seconds", job.getKey(), ft,
                trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);

        // job4 will run 6 times (run once and repeat 5 more times) job4 will repeat every 10 seconds
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job4", "group1").build();
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger4", "group1").startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(5)).build();
        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} will run at:{} and repeat:{}, times, every {} seconds", job.getKey(), ft,
                trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);

        // job5 will run once, one minutes in the future
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job5", "group1").build();
        trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger5", "group1")
                .startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.MINUTE)).build();
        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} will run at:{} and repeat:{}, times, every {} seconds", job.getKey(), ft,
                trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);

        // job6 will run indefinitely, every 40 seconds
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job6", "group1").build();
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger6", "group1").startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(40).repeatForever()).build();
        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} will run at:{} and repeat:{}, times, every {} seconds", job.getKey(), ft,
                trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);
        log.info("------- Starting Scheduler ----------------");

        // All of the jobs have been added to the scheduler, but none of the jobs
        // will run until the scheduler has been started
        scheduler.start();
        log.info("------- Started Scheduler -----------------");

        // jobs can also be scheduled after start() has been called...
        // job7 will repeat 20 times, repeat every five minutes
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job7", "group1").build();
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger7", "group1").startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).withRepeatCount(20)).build();
        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} will run at:{} and repeat:{}, times, every {} seconds", job.getKey(), ft,
                trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);

        // jobs can be fired directly...(rather than waiting for a trigger)
        // 工作可以直接被激发…(而不是等待触发)
        job = JobBuilder.newJob(SimpleJob.class).withIdentity("job8", "group1").storeDurably().build();
        scheduler.addJob(job, true);
        log.info("'Manually' triggering job8...");
        scheduler.triggerJob(JobKey.jobKey("job8", "group1"));

        log.info("------- Waiting 30 seconds... --------------");
        try {
            // wait 33 seconds to show jobs
            Thread.sleep(30L * 1000L);
            // executing...
        } catch (Exception e) {
            //
        }

        // jobs can be re-scheduled...
        // job 7 will run immediately and repeat 10 times for every second
        log.info("------- Rescheduling... --------------------");
        trigger = TriggerBuilder.newTrigger().withIdentity("trigger7", "group1").startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).withRepeatCount(2)).build();
        ft = scheduler.rescheduleJob(trigger.getKey(), trigger);
        log.info("job7 rescheduled to run at: {}", ft);

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

        // display some stats about the schedule that just ran
        SchedulerMetaData metaData = scheduler.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

    public static void main(String[] args) throws SchedulerException {
        SimpleTriggerExample example = new SimpleTriggerExample();
        example.run();
    }
}
