package com.stackstone.study.springboot.quartz.tutorials.example7;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Copyright 2020 Oriental Standard All rights reserved.
 *
 * @author: LiLei
 * @className: DumbInterruptableJob
 * @createTime: 2020/10/15 10:41
 * @description: A dumb implementation of an InterruptableJob, for unit testing purposes.
 */
@Slf4j
public class DumbInterruptableJob implements InterruptableJob {

    /**
     * has the job been interrupted?
     */
    private boolean interrupted = false;

    /**
     * job name
     */
    private JobKey jobKey = null;

    /**
     * <p>
     * Called by the <code>{@link Scheduler}</code> when a user
     * interrupts the <code>Job</code>.
     * </p>
     *
     * @return void (nothing) if job interrupt is successful.
     * @throws UnableToInterruptJobException if there is an exception while interrupting the job.
     */
    @Override
    public void interrupt() throws UnableToInterruptJobException {
        log.info("---" + jobKey + "  -- INTERRUPTING --");
        interrupted = true;
    }

    /**
     * <p>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a <code>{@link org.quartz.Trigger}</code>
     * fires that is associated with the <code>Job</code>.
     * </p>
     *
     * @throws JobExecutionException if there is an exception while executing the job.
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        jobKey = context.getJobDetail().getKey();
        log.info("---- " + jobKey + " executing at " + LocalDateTime.now());

        try {
            // main job loop... see the JavaDOC for InterruptableJob for discussion...
            // do some work... in this example we are 'simulating' work by sleeping... :)
            for (int i = 0; i < 4; i++) {
                try {
                    Thread.sleep(1000L);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // periodically check if we've been interrupted...
                if (interrupted) {
                    log.info("--- " + jobKey + "  -- Interrupted... bailing out!");
                    return;
                    // could also choose to throw a JobExecutionException
                    // if that made for sense based on the particular job's responsibilities/behaviors
                } else {
                    log.info("--- " + jobKey + "  -- Not interrupted...");
                }
            }
        } finally {
            log.info("---- " + jobKey + " completed at " + new Date());
        }
    }
}
