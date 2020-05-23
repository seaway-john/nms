package com.oem.nms.log.listen;

import com.oem.nms.common.mq.entity.LogMessage;
import com.oem.nms.common.mq.util.MqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Seaway John
 */
@Slf4j
@Component
@RabbitListener(
        bindings = @QueueBinding(
                exchange = @Exchange(value = MqConstants.MQ_EXCHANGE_LOG, type = ExchangeTypes.DIRECT, autoDelete = "false"),
                key = MqConstants.MQ_ROUTING_KEY_LOG,
                value = @Queue(value = MqConstants.MQ_QUEUE_LOG, autoDelete = "false")
        )
)
public class LogMqReceiver {

    @Autowired
    public LogMqReceiver() {
    }

    @RabbitHandler
    public void handle(LogMessage logMessage) {
        log.info("Receive log mq {}", logMessage);
    }

}
