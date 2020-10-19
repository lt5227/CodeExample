package com.stackstone.study.springboot.rabbitmq.listener.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * PushOrderRecordEvent.java
 * Spring 事件驱动模型<br/>
 * 类似于 Message，相当于RabbitMQ的Message一串二进制数据流
 * <p>Copyright (c) 2020 StackStone All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020-10-19 17:15:48
 * @since 1.0.0
 */
@Getter
@Setter
public class PushOrderRecordEvent extends ApplicationEvent {

    private String orderNo;
    private String orderType;

    public PushOrderRecordEvent(Object source, String orderNo, String orderType) {
        super(source);
        this.orderNo = orderNo;
        this.orderType = orderType;
    }
}
