package com.oem.nms.common.mq.config;

import com.oem.nms.common.mq.util.MqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Seaway John
 */
@Slf4j
@Configuration
public class MqLogConfig {

    /**
     * 目标交换器
     */
    @Bean
    public DirectExchange exchangeLog() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("alternate-exchange", MqConstants.MQ_EXCHANGE_LOG_AE);

        return new DirectExchange(MqConstants.MQ_EXCHANGE_LOG, true, false, map);
    }

    /**
     * 备份交换器 Alternate Exchange
     */
    @Bean
    public FanoutExchange exchangeLogAe() {
        return new FanoutExchange(MqConstants.MQ_EXCHANGE_LOG_AE, true, false, null);
    }

    /**
     * 死信交换器 Dead Letter Exchange
     */
    @Bean
    public TopicExchange exchangeLogDle() {
        return new TopicExchange(MqConstants.MQ_EXCHANGE_LOG_DLE, true, false, null);
    }

    @Bean
    public Queue queueLog() {
        Map<String, Object> args = new HashMap<>(8);
        args.put("x-dead-letter-exchange", MqConstants.MQ_EXCHANGE_LOG_DLE);
        args.put("x-dead-letter-routing-key", MqConstants.MQ_ROUTING_KEY_LOG_DLE_LOG);
        args.put("x-message-ttl", 5000);

        return new Queue(MqConstants.MQ_QUEUE_LOG, true, false, false, args);
    }

    @Bean
    public Queue queueLogAe() {
        return new Queue(MqConstants.MQ_QUEUE_LOG_AE, true, false, false, null);
    }

    @Bean
    public Queue queueLogDle() {
        return new Queue(MqConstants.MQ_QUEUE_LOG_DLE, true, false, false, null);
    }

    @Bean
    public Binding bindingLog() {
        return BindingBuilder.bind(queueLog()).to(exchangeLog()).with(MqConstants.MQ_ROUTING_KEY_LOG);
    }

    @Bean
    public Binding bindingLogAe() {
        return BindingBuilder.bind(queueLogAe()).to(exchangeLogAe());
    }

    @Bean
    public Binding bindingLogDle() {
        return BindingBuilder.bind(queueLogDle()).to(exchangeLogDle()).with(String.format("%s.*", MqConstants.MQ_ROUTING_KEY_LOG_DLE));
    }

}
