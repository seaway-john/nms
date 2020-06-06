package com.oem.nms.common.rabbitmq.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oem.nms.common.entity.response.Response;
import com.oem.nms.common.entity.response.ResponseCode;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.synchronoss.cloud.nio.multipart.util.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author Seaway John
 */
public class MqUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    private MqUtil() {
    }

    public static <T> Response<T> handle(Message message, Channel channel) {
        String mqId = message.getMessageProperties().getHeader("spring_listener_return_correlation");
        long mqTag = message.getMessageProperties().getDeliveryTag();

        try {
            /* 设置Qos机制
             * 第一个参数：单条消息的大小(0表示即无限制)
             * 第二个参数：每次处理消息的数量
             * 第三个参数：是否为consumer级别（false表示仅当前channel有效）
             */
            channel.basicQos(0, 1, false);

            /* 手动应答消息
             * 第一个参数是所确认消息的标识
             * 第二参数是是否批量确认
             */
            channel.basicAck(mqTag, true);
        } catch (Exception e) {
            try {
                channel.basicReject(mqTag, false);
            } catch (IOException e1) {
                return new Response<>(ResponseCode.ERROR_SERVER_ERROR, String.format("Failed to send nack, mq ID %s, reason %s", mqId, e1.getMessage()));
            }

            return new Response<>(ResponseCode.ERROR_SERVER_ERROR, String.format("Failed to ack, mq ID %s, reason %s", mqId, e.getMessage()));
        }

        ByteArrayInputStream bas = null;
        ObjectInputStream ois = null;
        try {
            bas = new ByteArrayInputStream(message.getBody());
            ois = new ObjectInputStream(bas);

            return new Response<>((T) ois.readObject());
        } catch (Exception e) {
            return new Response<>(ResponseCode.ERROR_SERVER_ERROR, String.format("Failed to read, mq ID %s, reason %s", mqId, e.getMessage()));
        } finally {
            IOUtils.closeQuietly(ois);
            IOUtils.closeQuietly(bas);
        }
    }


}
