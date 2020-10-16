package com.stackstone.study.rabbitmq.subscribe;

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
 * @className: Producer
 * @createTime 2020/10/5 16:10
 * @description: 生产者
 * 订阅广播式消息模型
 * https://www.rabbitmq.com/tutorials/tutorial-three-java.html
 */
public class SubscribeProducer {

    public static final String EXCHANGE_NAME = "rabbit:mq01:exchange:e01";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // fanout-exchange 无意识分发消息模型
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            String message = "fanoutExchange-publish的消息-02";
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发送消息成功---> ");
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
