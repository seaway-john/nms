package com.oem.nms.log.listen;

import com.oem.nms.common.entity.response.Response;
import com.oem.nms.common.rabbitmq.entity.LogMessage;
import com.oem.nms.common.rabbitmq.util.MqConstants;
import com.oem.nms.common.rabbitmq.util.MqUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Seaway John
 */
@Slf4j
@Component
public class LogMqReceiver {

    @Autowired
    public LogMqReceiver() {
    }

    @RabbitHandler
    @RabbitListener(queues = MqConstants.MQ_QUEUE_LOG)
    public void handle(Message message, Channel channel) {
        Response<LogMessage> response = MqUtil.handle(message, channel);
        if (response.isError()) {
            log.warn("Error in handle mq snmp trap: {}", response.getMessage());
            return;
        }

        // TODO: 2020-05-24
    }

}
