package com.stackstone.study.sentinel.demo;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.stackstone.study.hystrix.demo.DemoServiceProvider;
import com.stackstone.study.hystrix.demo.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * SentinelTestController
 *
 * @author LiLei
 * @date 2021/8/27
 * @since 1.0.0
 */
@Slf4j
@RestController
public class SentinelTestController {

    @Autowired
    private DemoServiceProvider provider;

    @GetMapping("/sentinelTest1")
    public List<Product> test1(String key) throws BlockException {
//        init();
        List<Product> result = new ArrayList<>();
        Entry test1 = null;
        try {
            test1 = SphU.entry("sentinelTest1");
            result.addAll(provider.queryProducts(key));
            return result;
        } catch (BlockException e1) {
            log.error("BlockException ", e1);
            throw e1;
        } catch (Exception e2) {
            // biz exception
            log.error("Exception ", e2);
            throw e2;
        } finally {
            if (test1 != null) {
                test1.exit();
            }
        }
    }

    static {
        List<FlowRule> rules = new ArrayList<FlowRule>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource("sentinelTest1");
        // set limit concurrent thread for 'methodA' to 20
        rule1.setCount(1);
        rule1.setGrade(RuleConstant.FLOW_GRADE_THREAD);
        rule1.setLimitApp("default");

        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }

    public static void init() {
        List<FlowRule> rules = new ArrayList<FlowRule>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource("sentinelTest1");
        // set limit concurrent thread for 'methodA' to 20
        rule1.setCount(1);
        rule1.setGrade(RuleConstant.FLOW_GRADE_THREAD);
        rule1.setLimitApp("default");

        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }
}