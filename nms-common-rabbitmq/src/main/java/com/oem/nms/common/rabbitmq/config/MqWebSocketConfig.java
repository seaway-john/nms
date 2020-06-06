package com.oem.nms.common.rabbitmq.config;

import com.oem.nms.common.rabbitmq.util.MqConstants;
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
public class MqWebSocketConfig {

    /**
     * 目标交换器
     */
    @Bean
    public DirectExchange exchangeWebSocket() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("alternate-exchange", MqConstants.MQ_EXCHANGE_WEBSOCKET_AE);

        return new DirectExchange(MqConstants.MQ_EXCHANGE_WEBSOCKET, true, false, map);
    }

    /**
     * 备份交换器 Alternate Exchange
     */
    @Bean
    public FanoutExchange exchangeWebSocketAe() {
        return new FanoutExchange(MqConstants.MQ_EXCHANGE_WEBSOCKET_AE, true, false, null);
    }

    /**
     * 死信交换器 Dead Letter Exchange
     */
    @Bean
    public TopicExchange exchangeWebSocketDle() {
        return new TopicExchange(MqConstants.MQ_EXCHANGE_WEBSOCKET_DLE, true, false, null);
    }

    @Bean
    public Queue queueWebSocketTopo() {
        Map<String, Object> args = new HashMap<>(8);
        args.put("x-dead-letter-exchange", MqConstants.MQ_EXCHANGE_WEBSOCKET_DLE);
        args.put("x-dead-letter-routing-key", MqConstants.MQ_ROUTING_KEY_WEBSOCKET_DLE_SNMP_TRAP);
        args.put("x-message-ttl", 5000);

        return new Queue(MqConstants.MQ_QUEUE_WEBSOCKET_TOPO, true, false, false, args);
    }

    @Bean
    public Queue queueWebSocketSnmpTrap() {
        Map<String, Object> args = new HashMap<>(8);
        args.put("x-dead-letter-exchange", MqConstants.MQ_EXCHANGE_WEBSOCKET_DLE);
        args.put("x-dead-letter-routing-key", MqConstants.MQ_ROUTING_KEY_WEBSOCKET_DLE_TOPO);
        args.put("x-message-ttl", 5000);

        return new Queue(MqConstants.MQ_QUEUE_WEBSOCKET_SNMP_TRAP, true, false, false, args);
    }

    @Bean
    public Queue queueWebSocketAe() {
        return new Queue(MqConstants.MQ_QUEUE_WEBSOCKET_AE, true, false, false, null);
    }

    @Bean
    public Queue queueWebSocketDle() {
        return new Queue(MqConstants.MQ_QUEUE_WEBSOCKET_DLE, true, false, false, null);
    }

    @Bean
    public Binding bindingWebSocketTopo() {
        return BindingBuilder.bind(queueWebSocketTopo()).to(exchangeWebSocket()).with(MqConstants.MQ_ROUTING_KEY_WEBSOCKET_TOPO);
    }

    @Bean
    public Binding bindingWebSocketSnmpTrap() {
        return BindingBuilder.bind(queueWebSocketSnmpTrap()).to(exchangeWebSocket()).with(MqConstants.MQ_ROUTING_KEY_WEBSOCKET_SNMP_TRAP);
    }

    @Bean
    public Binding bindingWebSocketAe() {
        return BindingBuilder.bind(queueWebSocketAe()).to(exchangeWebSocketAe());
    }

    @Bean
    public Binding bindingWebSocketDle() {
        return BindingBuilder.bind(queueWebSocketDle()).to(exchangeWebSocketDle()).with(String.format("%s.*", MqConstants.MQ_ROUTING_KEY_WEBSOCKET_DLE));
    }

}
