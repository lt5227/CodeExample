package com.stackstone.study.springboot.rabbitmq.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.stackstone.study.springboot.rabbitmq.core.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AckAndConcurrentListener
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 16:59
 * @since 1.0.0
 */
@Component
@Slf4j
public class AckAndConcurrentListener implements ChannelAwareMessageListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long tag = message.getMessageProperties().getDeliveryTag();
        try {
            byte[] msg = message.getBody();
            UserDTO userDTO = objectMapper.readValue(msg, UserDTO.class);
            if (userDTO.getId() == 1) {
                throw new IllegalArgumentException("模拟数据错误!");
            }
            log.info("简单消息监听确认机制监听到消息：{}", userDTO);
            channel.basicAck(tag, true);
        } catch (Exception e) {
            log.error("简单消息监听确认机制发生异常", e.fillInStackTrace());
            channel.basicReject(tag, false);
        }
    }
}
