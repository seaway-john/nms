package com.oem.nms.common.mq.service;

import com.oem.nms.common.entity.request.RequestInfo;
import com.oem.nms.common.mq.entity.LogMessage;
import com.oem.nms.common.mq.entity.LogMessageLevel;
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
public class MqSender {

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public MqSender(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendLog(RequestInfo requestInfo, LogMessageLevel level, String message) {
        LogMessage logMessage = new LogMessage();
        logMessage.setIp(requestInfo.getIp());
        logMessage.setUsername(requestInfo.getUsername());
        logMessage.setLevel(level);
        logMessage.setMessage(message);

        amqpTemplate.convertAndSend(MqConstants.MQ_EXCHANGE_LOG, MqConstants.MQ_ROUTING_KEY_LOG, logMessage);
    }

    public void sendSnmpTrap(String message) {
        WebsocketMessage websocketMessage = new WebsocketMessage();
        websocketMessage.setMessage(message);

        amqpTemplate.convertAndSend(MqConstants.MQ_EXCHANGE_WEBSOCKET, MqConstants.MQ_ROUTING_KEY_WEBSOCKET_SNMP_TRAP, websocketMessage);
    }

    public void sendTopo(String message) {
        WebsocketMessage websocketMessage = new WebsocketMessage();
        websocketMessage.setMessage(message);

        amqpTemplate.convertAndSend(MqConstants.MQ_EXCHANGE_WEBSOCKET, MqConstants.MQ_ROUTING_KEY_WEBSOCKET_TOPO, websocketMessage);
    }

}
