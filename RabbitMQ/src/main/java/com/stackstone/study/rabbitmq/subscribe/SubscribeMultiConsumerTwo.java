package com.stackstone.study.rabbitmq.subscribe;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

/**
 * Copyright 2020 StackStone All rights reserved.
 *
 * @author: LiLei
 * @className: MultiConsumerOne
 * @createTime 2020/10/5 16:16
 * @description: 消费者1
 */
public class SubscribeMultiConsumerTwo {
    public static final String EXCHANGE_NAME = "rabbit:mq01:exchange:e01";
    public static final String QUEUE_NAME_02 = "rabbit:mq02:queue:q02";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // fanout-exchange 无意识分发消息模型-绑定
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        channel.queueDeclare(QUEUE_NAME_02, true, false, false, null);
        channel.queueBind(QUEUE_NAME_02, EXCHANGE_NAME, "");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("消费者2接收到消息成功：'" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME_02, true, deliverCallback, consumerTag -> {
        });
    }
}
