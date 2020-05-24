package com.oem.nms.websocket.listen;

import com.oem.nms.common.entity.response.Response;
import com.oem.nms.common.mq.util.MqConstants;
import com.oem.nms.common.mq.util.MqUtil;
import com.oem.nms.websocket.util.WebsocketConstants;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;

/**
 * @author Seaway John
 */
@Slf4j
@Component
public class SnmpTrapMqReceiver {

    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public SnmpTrapMqReceiver(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitHandler
    @RabbitListener(queues = MqConstants.MQ_QUEUE_WEBSOCKET_SNMP_TRAP)
    public void handle(Message message, Channel channel) {
        Response<WebSocketMessage> response = MqUtil.handle(message, channel);
        if (response.isError()) {
            log.warn("Error in handle mq snmp trap: {}", response.getMessage());
            return;
        }

        messagingTemplate.convertAndSend(WebsocketConstants.WEBSOCKET_TOPIC_SNMP_TRAP, response.getData());
    }

}
