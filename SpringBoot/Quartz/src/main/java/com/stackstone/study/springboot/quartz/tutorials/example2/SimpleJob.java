package com.stackstone.study.springboot.quartz.tutorials.example2;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import java.time.LocalDateTime;

/**
 * Copyright 2020 Oriental Standard All rights reserved.
 *
 * @author: LiLei
 * @className: SimpleJob
 * @createTime: 2020/10/14 15:14
 * @description: This is just a simple job that gets fired off many times by example 1
 */
@Slf4j
public class SimpleJob implements Job {
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
        // This job simply prints out its job name and the datetime that it's running
        JobKey jobKey = context.getJobDetail().getKey();
        log.info("SimpleJob says:{} executing at {}", jobKey, LocalDateTime.now());
    }
}
