package com.stackstone.study.sentinel;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import java.util.Map;

/**
 * Copyright 2021 PatSnap All rights reserved.
 * HelloSentinel
 *
 * @author LiLei
 * @date 2021/8/27
 * @since 1.0.0
 */
public class HelloSentinel {
    public static void main(String[] args) {
        Entry entry = null;
        try {
            entry = SphU.entry("Hello World!");
            System.out.println("hello world!");
        } catch (BlockException e) {
            // 处理被流控的逻辑
            System.out.println("blocked!");
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }
}
