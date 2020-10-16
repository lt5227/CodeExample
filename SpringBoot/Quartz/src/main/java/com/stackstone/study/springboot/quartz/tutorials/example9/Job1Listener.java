package com.stackstone.study.springboot.quartz.tutorials.example9;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

/**
 * Copyright 2020 Oriental Standard All rights reserved.
 *
 * @author: LiLei
 * @className: Job1Listener
 * @createTime: 2020/10/15 11:22
 * @description: 工作1侦听器
 */
@Slf4j
public class Job1Listener implements JobListener {
    @Override
    public String getName() {
        return "job1_to_job2";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.info("Job1Listener says: Job Is about to be executed.");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        log.info("Job1Listener says: Job Execution was vetoed.");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.info("Job1Listener says: Job was executed.");
        // Simple job #2
        JobDetail job2 = JobBuilder.newJob(SimpleJob2.class).withIdentity("job2").build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("job2Trigger").startNow().build();
        try {
            // schedule the job to run!
            context.getScheduler().scheduleJob(job2, trigger);
        } catch (SchedulerException e) {
            log.warn("Unable to schedule job2!");
            e.printStackTrace();
        }
    }
}
