package com.oem.nms.common.rabbitmq.service;

import com.oem.nms.common.rabbitmq.entity.WebsocketMessage;
import com.oem.nms.common.rabbitmq.util.MqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Seaway John
 */
@Slf4j
@Component
public class MqWebSocketSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MqWebSocketSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

        //这是是设置回调能收到发送到响应
        rabbitTemplate.setConfirmCallback(this);
        //如果设置备份队列则不起作用
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(this);
    }

    public void sendSnmpTrap(String message) {
        WebsocketMessage websocketMessage = new WebsocketMessage();
        websocketMessage.setMessage(message);

        rabbitTemplate.convertAndSend(MqConstants.MQ_EXCHANGE_WEBSOCKET, MqConstants.MQ_ROUTING_KEY_WEBSOCKET_SNMP_TRAP, websocketMessage);
    }

    public void sendTopo(String message) {
        WebsocketMessage websocketMessage = new WebsocketMessage();
        websocketMessage.setMessage(message);

        rabbitTemplate.convertAndSend(MqConstants.MQ_EXCHANGE_WEBSOCKET, MqConstants.MQ_ROUTING_KEY_WEBSOCKET_TOPO, websocketMessage);
    }

    /**
     * ack确认
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("Success to confirm websocket message, correlationData {}, cause {}", correlationData, cause);
        } else {
            log.warn("Failed to confirm websocket message, correlationData {}, cause {}", correlationData, cause);
        }
    }

    /**
     * 消息发送到转换器的时候没有对列,配置了备份对列该回调则不生效
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.warn("Loss websocket message, exchange {}, routingKey {}, replyCode {}, replyText {}, message {}", exchange, routingKey, replyCode, replyText, message);
    }
}
