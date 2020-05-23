package com.oem.nms.common.mq.util;

/**
 * @author Seaway John
 */
public class MqConstants {

    public static final String MQ_EXCHANGE_WEBSOCKET = "exchange.websocket.direct";
    public static final String MQ_ROUTING_KEY_WEBSOCKET_SNMP_TRAP = "routing.key.websocket.snmp.trap";
    public static final String MQ_QUEUE_WEBSOCKET_SNMP_TRAP = "queue.websocket.snmp.trap";
    public static final String MQ_ROUTING_KEY_WEBSOCKET_TOPO = "routing.key.websocket.topo";
    public static final String MQ_QUEUE_WEBSOCKET_TOPO = "queue.websocket.topo";

    public static final String MQ_EXCHANGE_LOG = "exchange.log.direct";
    public static final String MQ_ROUTING_KEY_LOG = "routing.key.log";
    public static final String MQ_QUEUE_LOG = "queue.log";

    private MqConstants() {
    }

}
