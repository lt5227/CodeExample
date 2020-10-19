package com.stackstone.study.springboot.rabbitmq.listener;

import com.stackstone.study.springboot.rabbitmq.listener.event.PushOrderRecordEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * PushOrderRecordListener.java
 * <p>
 * 这就是监听器-跟RabbitMQ的Listener几乎是一个理念<br/>
 * "event绑定到一个listener"-> 一个event可以被帮绑定到多个listener
 *
 * <p>Copyright (c) 2020 StackStone All rights reserved.</p>
 *
 * @author LiLei
 * @date 2020-10-19 17:13:19
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@Slf4j
public class PushOrderRecordListener implements ApplicationListener<PushOrderRecordEvent> {
    @Override
    public void onApplicationEvent(PushOrderRecordEvent pushOrderRecordEvent) {
        log.info("监听到的下单记录：{}", pushOrderRecordEvent);
    }
}
