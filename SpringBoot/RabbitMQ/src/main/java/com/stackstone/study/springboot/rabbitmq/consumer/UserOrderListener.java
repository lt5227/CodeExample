package com.stackstone.study.springboot.rabbitmq.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.stackstone.study.springboot.rabbitmq.core.dto.UserOrderDTO;
import com.stackstone.study.springboot.rabbitmq.db.mysql.entity.UserOrderEntity;
import com.stackstone.study.springboot.rabbitmq.db.mysql.repository.UserOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * UserOrderListener
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020/10/21 17:31
 * @since 1.0.0
 */
@Component
@Slf4j
public class UserOrderListener implements ChannelAwareMessageListener {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserOrderRepository userOrderRepository;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long tag = message.getMessageProperties().getDeliveryTag();
        try {
            byte[] body = message.getBody();
            UserOrderDTO userOrderDTO = objectMapper.readValue(body, UserOrderDTO.class);
            log.info("监听到用户：{}", userOrderDTO);
            UserOrderEntity userOrderEntity = new UserOrderEntity();
            BeanUtils.copyProperties(userOrderDTO, userOrderEntity);
            userOrderEntity.setStatus(1);
            userOrderEntity.setCreateTime(LocalDateTime.now());
            userOrderRepository.save(userOrderEntity);
            channel.basicAck(tag, true);
        } catch (Exception e) {
            log.error("用户商城下单 发生异常:", e.fillInStackTrace());
            channel.basicReject(tag, false);
        }
    }
}
