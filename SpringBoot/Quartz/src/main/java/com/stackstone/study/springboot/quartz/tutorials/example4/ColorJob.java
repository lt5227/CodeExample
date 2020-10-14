package com.stackstone.study.springboot.quartz.tutorials.example4;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Copyright 2020 Oriental Standard All rights reserved.
 *
 * @author: LiLei
 * @className: ColorJob
 * @createTime: 2020/10/14 16:31
 * @description: This is just a simple job that receives parameters and maintains state
 */

@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ColorJob implements Job {
    /**
     * parameter names specific to this job
     */
    public static final String FAVORITE_COLOR = "favorite color";

    public static final String EXECUTION_COUNT = "count";

    /**
     * Since Quartz will re-instantiate a class every time it gets executed, members non-static member variables can
     * not be used to maintain state!
     */
    private int counter = 1;


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
        // This job simply prints out its job name and the date and time that it is running
        JobKey jobKey = context.getJobDetail().getKey();
        // Grab and print passed parameters
        JobDataMap data = context.getJobDetail().getJobDataMap();
        String favoriteColor = data.getString(FAVORITE_COLOR);
        int count = data.getInt(EXECUTION_COUNT);
        log.info("ColorJob: " + jobKey + " executing at " + LocalDateTime.now() + "\n" +
                "  favorite color is " + favoriteColor + "\n" +
                "  execution count (from job map) is " + count + "\n" +
                "  execution count (from job member variable) is " + counter);
        // increment the count and store it back into the job map so that job state can be properly maintained
        count++;
        data.put(EXECUTION_COUNT, count);
        // Increment the local member variable
        // This serves no real purpose since job state can not be maintained via member variables!
        counter++;
    }
}
