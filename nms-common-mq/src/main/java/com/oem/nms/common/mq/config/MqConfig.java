package com.oem.nms.common.mq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author Seaway John
 */
@Slf4j
@Configuration
public class MqConfig {

    private final CachingConnectionFactory connectionFactory;

    @Autowired
    public MqConfig(CachingConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * 如果需要在生产者需要消息发送后的回调，需要对rabbitTemplate设置ConfirmCallback对象
     * 由于不同的生产者需要对应不同的ConfirmCallback
     * 如果rabbitTemplate设置为单例bean，则所有的rabbitTemplate实际的ConfirmCallback为最后一次申明的ConfirmCallback
     * 所以在每次注入的时候，SCOPE_PROTOTYPE会自动创建一个新的bean实例
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        return template;
    }

}
