package com.stackstone.study.springboot.rabbitmq.config.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * LogQueueConfig
 * 邮件发送队列消息模型
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 19:13
 * @since 1.0.0
 */
@Configuration
public class MailQueueConfig {
    @Autowired
    private Environment env;

    /* 邮件发送消息队列模型构建 */

    /**
     * 定义交换机
     */
    @Bean
    public TopicExchange mailExchange() {
        return new TopicExchange(env.getProperty("mq.mail.info.exchange"), true, false);
    }

    /**
     * 定义队列
     */
    @Bean
    public Queue mailQueue() {
        return new Queue(Objects.requireNonNull(env.getProperty("mq.mail.info.queue")), true);
    }

    /**
     * 交换机根据RoutingKey和队列绑定
     */
    @Bean
    public Binding mailBinding() {
        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(env.getProperty("mq.mail.info.routingKey"));
    }
}
