package com.oem.nms.common.mq.service;

import com.oem.nms.common.mq.entity.WebsocketMessage;
import com.oem.nms.common.mq.util.MqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Seaway John
 */
@Slf4j
@Component
public class MqClient {

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public MqClient(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendWebsocket(WebsocketMessage websocketMessage) {
        amqpTemplate.convertAndSend(MqConstants.MQ_EXCHANGE_WEBSOCKET, MqConstants.MQ_ROUTING_KEY_WEBSOCKET, websocketMessage);
    }


}
