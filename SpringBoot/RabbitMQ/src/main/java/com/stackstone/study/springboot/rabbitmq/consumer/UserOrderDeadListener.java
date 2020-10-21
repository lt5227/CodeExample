package com.stackstone.study.springboot.rabbitmq.consumer;

import com.stackstone.study.springboot.rabbitmq.db.mysql.entity.UserOrderEntity;
import com.stackstone.study.springboot.rabbitmq.db.mysql.repository.UserOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * UserOrderDeadListener
 * 用户订单死信队列监听器
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 22:54
 * @since 1.0.0
 */
@Component
@Slf4j
public class UserOrderDeadListener {
    @Autowired
    private UserOrderRepository userOrderRepository;

    @RabbitListener(queues = "${mq.deadQueue.userOrder.produce.queue}", containerFactory = "multiListenerContainer")
    public void consumeMessage(@Payload Integer id) {
        log.info("死信队列-用户下单超时未支付监听消息:{}", id);
        UserOrderEntity userOrderEntity = userOrderRepository.findByIdAndStatus(id, 1);
        if (userOrderEntity != null) {
            userOrderEntity.setStatus(3);
            userOrderEntity.setUpdateTime(LocalDateTime.now());
            userOrderRepository.save(userOrderEntity);
        } else {
            // 已支付-可能需要异步发送其他日志信息
        }
    }
}
