package com.stackstone.study.springboot.quartz.tutorials.example5;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Copyright 2020 Oriental Standard All rights reserved.
 *
 * @author: LiLei
 * @className: StatefulDumbJob
 * @createTime: 2020/10/14 18:10
 * @description: A dumb implementation of Job, for unit testing purposes.
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Slf4j
public class StatefulDumbJob implements Job {
    public static final String NUM_EXECUTIONS = "NumExecutions";

    public static final String EXECUTION_DELAY = "ExecutionDelay";

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
        System.err.println("---" + context.getJobDetail().getKey() + " executing.[" + LocalDateTime.now() + "]");
        JobDataMap map = context.getJobDetail().getJobDataMap();
        int executeCount = 0;
        if (map.containsKey(NUM_EXECUTIONS)) {
            executeCount = map.getInt(NUM_EXECUTIONS);
        }
        executeCount++;
        map.put(NUM_EXECUTIONS, executeCount);
        long delay = 5000L;
        if (map.containsKey(EXECUTION_DELAY)) {
            delay = map.getLong(EXECUTION_DELAY);
        }
        try {
            Thread.sleep(delay);
        } catch (Exception ignore) {
            //
        }
        System.err.println("  -" + context.getJobDetail().getKey() + " complete (" + executeCount + ").");
    }
}
