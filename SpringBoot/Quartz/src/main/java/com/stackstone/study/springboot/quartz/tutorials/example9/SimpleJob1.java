package com.stackstone.study.springboot.quartz.tutorials.example9;

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
 * @className: SimpleJob1
 * @createTime: 2020/10/15 11:18
 * @description: This is just a simple job that gets fired off many times by example 1
 */
@Slf4j
public class SimpleJob1 implements Job {

    /**
     * <p>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a
     * <code>{@link org.quartz.Trigger}</code> fires that is associated with
     * the <code>Job</code>.
     * </p>
     *
     * @throws JobExecutionException if there is an exception while executing the job.
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // This job simply prints out its job name and the
        // date and time that it is running
        JobKey jobKey = context.getJobDetail().getKey();
        log.info("SimpleJob1 says: " + jobKey + " executing at " + LocalDateTime.now());
    }
}
