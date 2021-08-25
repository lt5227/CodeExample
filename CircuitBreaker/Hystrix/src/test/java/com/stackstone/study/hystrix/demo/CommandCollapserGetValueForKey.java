package com.stackstone.study.hystrix.demo;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * CommandCollapserGetValueForKey
 *
 * @author LiLei
 * @date 2021/8/24
 * @since 1.0.0
 */
public class CommandCollapserGetValueForKey extends HystrixCollapser<List<String>, String, Integer> {

    private final Integer key;

    public CommandCollapserGetValueForKey(Integer key) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("demoCollapseCommand"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(5000)));
        System.out.println("CommandCollapserGetValueForKey...");
        this.key = key;
    }


    @Override
    public Integer getRequestArgument() {
        System.out.println("getRequestArgument...");
        return key;
    }

    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Integer>> requests) {
        System.out.println("HystrixCommandHystrixCommand========>");
        return new BatchCommand(requests);
    }

    @Override
    protected void mapResponseToRequests(List<String> batchResponse, Collection<CollapsedRequest<String, Integer>> requests) {
        System.out.println("mapResponseToRequests...");
        int count = 0;
        for (CollapsedRequest<String, Integer> request : requests) {
            request.setResponse(batchResponse.get(count++));
        }
    }

    private static final class BatchCommand extends HystrixCommand<List<String>> {
        private final Collection<CollapsedRequest<String, Integer>> requests;

        private BatchCommand(Collection<CollapsedRequest<String, Integer>> requests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("GetValueForKey")));
            System.out.println("BatchCommand...");
            this.requests = requests;
        }

        @Override
        protected List<String> run() throws Exception {
            System.out.println("run...");
            ArrayList<String> response = new ArrayList<>();
            for (CollapsedRequest<String, Integer> request: requests) {
                response.add("ValueForKey: " + request.getArgument());
            }
            return response;
        }
    }

    public static class UnitTest {
        @Test
        public void testCollapser() throws Exception {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            try {
                Future<String> f1 = new CommandCollapserGetValueForKey(1).queue();
                Future<String> f2 = new CommandCollapserGetValueForKey(2).queue();
                Future<String> f3 = new CommandCollapserGetValueForKey(3).queue();
                Future<String> f4 = new CommandCollapserGetValueForKey(4).queue();

                assertEquals("ValueForKey: 1", f1.get());
                assertEquals("ValueForKey: 2", f2.get());
                assertEquals("ValueForKey: 3", f3.get());
                assertEquals("ValueForKey: 4", f4.get());

                // assert that the batch command 'GetValueForKey' was in fact
                // executed and that it executed only once
                assertEquals(1, HystrixRequestLog.getCurrentRequest().getExecutedCommands().size());
                HystrixCommand<?> command = HystrixRequestLog.getCurrentRequest().getExecutedCommands().toArray(new HystrixCommand<?>[1])[0];
                // assert the command is the one we're expecting
                assertEquals("GetValueForKey", command.getCommandKey().name());
                // confirm that it was a COLLAPSED command execution
                assertTrue(command.getExecutionEvents().contains(HystrixEventType.COLLAPSED));
                // and that it was successful
                assertTrue(command.getExecutionEvents().contains(HystrixEventType.SUCCESS));
            } finally {
                context.shutdown();
            }
        }
    }
}
