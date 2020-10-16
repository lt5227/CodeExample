package com.stackstone.study.springboot.quartz.tutorials.example8;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Copyright 2020 Oriental Standard All rights reserved.
 *
 * @author: LiLei
 * @className: CalendarExample
 * @createTime: 2020/10/15 11:07
 * @description: This example will demonstrate how calendars can be
 * used to exclude periods of time when scheduling should not take place.
 */
@Slf4j
public class CalendarExample {

    public void run() throws SchedulerException {
        log.info("------- Initializing ----------------------");
        // First we must get a reference to a scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        log.info("------- Initialization Complete -----------");

        log.info("------- Scheduling Jobs -------------------");
        // Add the holiday calendar to the schedule
        AnnualCalendar holidays = new AnnualCalendar();
        // fourth of July (July 4)
        Calendar fourthOfJuly = new GregorianCalendar(LocalDate.now().getYear(), 6, 4);
        holidays.setDayExcluded(fourthOfJuly, true);
        // halloween (Oct 31)
        Calendar halloween = new GregorianCalendar(LocalDate.now().getYear(), 9, 31);
        holidays.setDayExcluded(halloween, true);
        // christmas (Dec 25)
        Calendar christmas = new GregorianCalendar(LocalDate.now().getYear(), 11, 25);
        holidays.setDayExcluded(christmas, true);
        // tell the schedule about our holiday calendar
        scheduler.addCalendar("holidays", holidays, false, false);

        // schedule a job to run hourly, starting on halloween at 10 am
        Date runDate = DateBuilder.dateOf(0, 0, 10, 31, 10);
        JobDetail job = JobBuilder.newJob(SimpleJob.class).withIdentity("job1", "group1").build();
        SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(runDate)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(1).repeatForever())
                .modifiedByCalendar("holidays").build();
        // schedule the job and print the first run date
        Date firstRunTime = scheduler.scheduleJob(job, trigger);

        // print out the first execution date. Note: Since Halloween (Oct 31) is a holiday,
        // then we will not run until the next day! (Nov 1)
        log.info(job.getKey() + " will run at: " + firstRunTime + " and repeat: " + trigger.getRepeatCount()
                + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

        // All of the jobs have been added to the scheduler, but none of the jobs
        // will run until the scheduler has been started
        log.info("------- Starting Scheduler ----------------");
        scheduler.start();

        // wait 30 seconds:
        // note: nothing will run
        log.info("------- Waiting 30 seconds... --------------");
        try {
            // wait 30 seconds to show jobs
            Thread.sleep(30L * 1000L);
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
        CalendarExample example = new CalendarExample();
        example.run();
    }
}
