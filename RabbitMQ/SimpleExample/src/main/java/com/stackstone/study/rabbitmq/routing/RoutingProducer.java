package com.stackstone.study.rabbitmq.routing;

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
 * @createTime 2020/10/5 16:34
 * @description: 生产者
 * 指定路由，订阅监听消费模型
 * direct-exchange+routingKey: 队列与交换机的绑定，有针对性的订阅监听消费
 */
public class RoutingProducer {
    public static final String EXCHANGE_NAME = "rabbit:mq03:exchange:e01";

    public static final String QUEUE_NAME_01 = "rabbit:mq03:queue:q01";
    public static final String QUEUE_NAME_02 = "rabbit:mq03:queue:q02";

    public static final String ROUTING_KEY_01 = "rabbit:mq03:routing:key:r01";
    public static final String ROUTING_KEY_02 = "rabbit:mq03:routing:key:r02";
    public static final String ROUTING_KEY_03 = "rabbit:mq03:routing:key:r03";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // direct-exchange+routingKey分发消息模型
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            // 队列1 交换机路由绑定
            channel.queueDeclare(QUEUE_NAME_01, true, false, false ,null);
            channel.queueBind(QUEUE_NAME_01, EXCHANGE_NAME, ROUTING_KEY_01);

            // 队列2 交换机路由绑定
            channel.queueDeclare(QUEUE_NAME_02, true, false, false, null);
            channel.queueBind(QUEUE_NAME_02, EXCHANGE_NAME, ROUTING_KEY_02);
            channel.queueBind(QUEUE_NAME_02, EXCHANGE_NAME, ROUTING_KEY_03);


            String message01 = "directExchange-publish我的消息-r01";
            String message02 = "directExchange-publish我的消息-r02";
            String message03 = "directExchange-publish我的消息-r03";

            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_01, null, message01.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_02, null, message02.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_03, null, message03.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发送消息成功---> ");
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
