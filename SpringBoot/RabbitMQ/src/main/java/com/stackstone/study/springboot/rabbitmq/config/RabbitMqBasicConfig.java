package com.stackstone.study.springboot.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

/**
 * RabbitMqConfig01
 * 单一消费者
 * RabbitMqTemplate发送组件配置 - 相当于ApplicationEventPublisher、rabbitmq客户端Amqp
 *
 * <p>Copyright (c) 2020 Oriental Standard All rights reserved.</p>
 *
 * @author LiLei
 * @version 1.0.0
 * @date 2020 /10/20 10:48
 * @since 1.0.0
 */
@Configuration
@Slf4j
public class RabbitMqBasicConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    @Autowired
    private Environment env;

    @Autowired
    private RabbitProperties rabbitProperties;

    /**
     * Single listener container simple rabbit listener container factory.
     * 单一消费者
     *
     * @return simple rabbit listener container factory
     */
    @Bean
    public SimpleRabbitListenerContainerFactory singleListenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // the message converter to use
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        // the minimum number of consumers to create.
        factory.setConcurrentConsumers(1);
        // the maximum number of consumers.
        factory.setMaxConcurrentConsumers(1);
        // the prefetch count
        factory.setPrefetchCount(1);
        // the batch size.
        factory.setBatchSize(1);
        return factory;
    }

    /**
     * Single listener container simple rabbit listener container factory.
     * 单一消费者
     *
     * 没有设置消息转换器，可以按字节方式监听消费，并转成字符串处理业务
     * @return simple rabbit listener container factory
     */
    @Bean
    public SimpleRabbitListenerContainerFactory singleListenerContainerNotSetMessageConverter() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // the minimum number of consumers to create.
        factory.setConcurrentConsumers(1);
        // the maximum number of consumers.
        factory.setMaxConcurrentConsumers(1);
        // the prefetch count
        factory.setPrefetchCount(1);
        // the batch size.
        factory.setBatchSize(1);
        return factory;
    }

    /**
     * Multi listener container simple rabbit listener container factory.
     *
     * @return the simple rabbit listener container factory
     */
    @Bean
    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory, connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        // 设置并发消费者
        factory.setConcurrentConsumers(rabbitProperties.getListener().getSimple().getConcurrency());
        // 设置最大同时消费者
        factory.setMaxConcurrentConsumers(rabbitProperties.getListener().getSimple().getMaxConcurrency());
        // 设置预取计数
        factory.setPrefetchCount(rabbitProperties.getListener().getSimple().getPrefetch());
        return factory;
    }


    /**
     * Rabbit template rabbit template.
     *
     * @return the rabbit template
     */
    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate() {
        // Set the confirm type to use; default CachingConnectionFactory.ConfirmType.NONE.
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // Set the mandatory flag when sending messages; only applies if a returnCallback had been provided
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) ->
                log.info("消息发送成功：correlationData({}) ,ack({}) ,cause({})", correlationData, ack, cause)
        );
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) ->
                log.info("消息丢失：exchange({}), route({}), replyCode({}), replyText({}), message:{}", exchange,
                        routingKey, replyCode, replyText, message)
        );
        return rabbitTemplate;
    }
}
