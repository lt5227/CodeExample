package com.stackstone.study.springboot.quartz.tutorials.example9;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

/**
 * Demonstrates the behavior of <code>JobListener</code>s. In particular, this example will use a job listener to
 * trigger another job after one job succesfully executes.
 *
 * @author: LiLei
 * @className: ListenerExample
 * @createTime: 2020/10/15 11:26
 * @description: 侦听器的例子
 */
@Slf4j
public class ListenerExample {

    public void run() throws SchedulerException {
        log.info("------- Initializing ----------------------");
        // First we must get a reference to a scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        log.info("------- Initialization Complete -----------");

        log.info("------- Scheduling Jobs -------------------");
        // schedule a job to run immediately
        JobDetail job = JobBuilder.newJob(SimpleJob1.class).withIdentity("job1").build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1").startNow().build();
        // Set up the listener
        JobListener listener = new Job1Listener();
        Matcher<JobKey> matcher = KeyMatcher.keyEquals(job.getKey());
        scheduler.getListenerManager().addJobListener(listener, matcher);
        // schedule the job to run
        scheduler.scheduleJob(job, trigger);

        // All of the jobs have been added to the scheduler, but none of the jobs
        // will run until the scheduler has been started
        log.info("------- Starting Scheduler ----------------");
        scheduler.start();
        // wait 30 seconds:
        log.info("------- Waiting 30 seconds... --------------");
        try {
            // wait 30 seconds to show jobs
            Thread.sleep(40L * 1000L);
            // executing...
        } catch (Exception e) {
            //
        }
        // shut down the scheduler
        log.info("------- Shutting Down ---------------------");
        scheduler.shutdown(true);
        log.info("------- Shutdown Complete -----------------");

        SchedulerMetaData metaData = scheduler.getMetaData();
        log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

    public static void main(String[] args) throws Exception {
        ListenerExample example = new ListenerExample();
        example.run();
    }
}
