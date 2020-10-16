package com.stackstone.study.springboot.quartz.tutorials.example10;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.impl.StdSchedulerFactory;

import java.io.File;

/**
 * Copyright 2020 Oriental Standard All rights reserved.
 *
 * @author: LiLei
 * @className: PluginExample
 * @createTime: 2020/10/15 11:43
 * @description: This example will spawn a large number of jobs to run
 */
@Slf4j
public class PlugInExample {

    public void run() throws SchedulerException {
        // First we must get a reference to a scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        log.info("------- Initialization Complete -----------");

        log.info("------- (Not Scheduling any Jobs - relying on XML definitions --");
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
        PlugInExample example = new PlugInExample();
        example.run();
    }
}
