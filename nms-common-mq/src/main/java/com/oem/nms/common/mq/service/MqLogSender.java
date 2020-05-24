package com.oem.nms.common.mq.service;

import com.oem.nms.common.entity.request.RequestInfo;
import com.oem.nms.common.mq.entity.LogMessage;
import com.oem.nms.common.mq.entity.LogMessageLevel;
import com.oem.nms.common.mq.util.MqConstants;
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
public class MqLogSender implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MqLogSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;

        //这是是设置回调能收到发送到响应
        rabbitTemplate.setConfirmCallback(this);
        //如果设置备份队列则不起作用
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(this);
    }

    public void send(RequestInfo requestInfo, LogMessageLevel level, String message) {
        LogMessage logMessage = new LogMessage();
        logMessage.setIp(requestInfo.getIp());
        logMessage.setUsername(requestInfo.getUsername());
        logMessage.setLevel(level);
        logMessage.setMessage(message);

        rabbitTemplate.convertAndSend(MqConstants.MQ_EXCHANGE_LOG, MqConstants.MQ_ROUTING_KEY_LOG, logMessage);
    }

    /**
     * ack确认
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("Success to confirm log message, correlationData {}, cause {}", correlationData, cause);
        } else {
            log.warn("Failed to confirm log message, correlationData {}, cause {}", correlationData, cause);
        }
    }

    /**
     * 消息发送到转换器的时候没有对列,配置了备份对列该回调则不生效
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.warn("Loss log message, exchange {}, routingKey {}, replyCode {}, replyText {}, message {}", exchange, routingKey, replyCode, replyText, message);
    }
}
