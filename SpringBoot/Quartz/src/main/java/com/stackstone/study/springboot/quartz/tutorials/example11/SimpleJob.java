package com.stackstone.study.springboot.quartz.tutorials.example11;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Copyright 2020 Oriental Standard All rights reserved.
 *
 * @author: LiLei
 * @className: SimpleJob
 * @createTime: 2020/10/15 14:35
 * @description: This is just a simple job that gets fired off many times by example 1
 */
@Slf4j
public class SimpleJob implements Job {

    public static final String DELAY_TIME = "delay time";

    /**
     * <p>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a <code>{@link org.quartz.Trigger}</code> fires that
     * is associated with the <code>Job</code>.
     * </p>
     *
     * @throws JobExecutionException if there is an exception while executing the job.
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // This job simply prints out its job name and the
        // date and time that it is running
        JobKey jobKey = context.getJobDetail().getKey();
        log.info("Executing job: " + jobKey + " executing at " + LocalDateTime.now());
        // wait for a period of time
        long delayTime = context.getJobDetail().getJobDataMap().getLong(DELAY_TIME);
        try {
            Thread.sleep(delayTime);
        } catch (Exception e) {
            //
        }
        log.info("Finished Executing job: " + jobKey + " at " + LocalDateTime.now());
    }
}
