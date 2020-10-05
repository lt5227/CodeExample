package com.stackstone.study.rabbitmq.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * Copyright 2020 StackStone All rights reserved.
 *
 * @author: LiLei
 * @className: TopicProducer
 * @createTime 2020/10/5 17:24
 * @description: 生产者
 * https://www.rabbitmq.com/tutorials/tutorial-five-java.html
 */
public class TopicProducer {

    public static final String EXCHANGE_NAME = "rabbit:mq04:exchange:e01";

    public static final String KEY01 = "rabbit:mq04:routing:key:r.orange";
    public static final String KEY02 = "rabbit:mq04:routing:key:r.orange.apple";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            String message = "topicExchange-publish我的消息";
            channel.basicPublish(EXCHANGE_NAME, KEY01, null, message.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, KEY02, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发送消息成功---> ");
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
