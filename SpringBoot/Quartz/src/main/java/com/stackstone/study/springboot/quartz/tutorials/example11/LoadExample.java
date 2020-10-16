package com.stackstone.study.springboot.quartz.tutorials.example11;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Copyright 2020 Oriental Standard All rights reserved.
 *
 * @author: LiLei
 * @className: LoadExample
 * @createTime: 2020/10/15 14:37
 * @description: This example will spawn a large number of jobs to run
 */
@Slf4j
public class LoadExample {
    private int numberOfJobs = 500;

    public LoadExample(int numberOfJobs) {
        this.numberOfJobs = numberOfJobs;
    }

    public void run() throws SchedulerException {
        // First we must get a reference to a scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        log.info("------- Initialization Complete -----------");

        // schedule 500 jobs to run
        for (int count = 1; count <= numberOfJobs; count++) {
            // ask scheduler to re-execute this job if it was in progress when the scheduler went down...
            JobDetail job = newJob(SimpleJob.class).withIdentity("job" + count, "group_1")
                    .requestRecovery().build();
            // tell the job to delay some small amount... to simulate work...
            long timeDelay = (long) (java.lang.Math.random() * 2500);
            job.getJobDataMap().put(SimpleJob.DELAY_TIME, timeDelay);
            Trigger trigger = newTrigger().withIdentity("trigger_" + count, "group_1")
                    // space fire times a small bit
                    .startAt(futureDate((10000 + (count * 100)), DateBuilder.IntervalUnit.MILLISECOND))
                    .build();
            scheduler.scheduleJob(job, trigger);
            if (count % 25 == 0) {
                log.info("...scheduled " + count + " jobs");
            }
        }
        log.info("------- Starting Scheduler ----------------");
        // start the schedule
        scheduler.start();
        log.info("------- Started Scheduler -----------------");
        log.info("------- Waiting five minutes... -----------");
        // wait five minutes to give our jobs a chance to run
        try {
            Thread.sleep(300L * 1000L);
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
        int numberOfJobs = 500;
        if (args.length == 1) {
            numberOfJobs = Integer.parseInt(args[0]);
        }
        if (args.length > 1) {
            System.out.println("Usage: java " + LoadExample.class.getName() + "[# of jobs]");
            return;
        }
        LoadExample example = new LoadExample(numberOfJobs);
        example.run();
    }
}
