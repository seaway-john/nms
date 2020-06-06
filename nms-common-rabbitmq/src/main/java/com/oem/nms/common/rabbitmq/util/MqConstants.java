package com.oem.nms.common.rabbitmq.util;

/**
 * @author Seaway John
 */
public class MqConstants {

    public static final String MQ_EXCHANGE_WEBSOCKET = "exchange.websocket";
    public static final String MQ_EXCHANGE_WEBSOCKET_AE = "exchange.websocket.ae";
    public static final String MQ_EXCHANGE_WEBSOCKET_DLE = "exchange.websocket.dle";
    public static final String MQ_ROUTING_KEY_WEBSOCKET_SNMP_TRAP = "routing.key.websocket.snmp.trap";
    public static final String MQ_ROUTING_KEY_WEBSOCKET_TOPO = "routing.key.websocket.topo";
    public static final String MQ_ROUTING_KEY_WEBSOCKET_AE = "routing.key.websocket.ae";
    public static final String MQ_ROUTING_KEY_WEBSOCKET_DLE = "routing.key.websocket.dle";
    public static final String MQ_ROUTING_KEY_WEBSOCKET_DLE_SNMP_TRAP = "routing.key.websocket.dle.snmp.trap";
    public static final String MQ_ROUTING_KEY_WEBSOCKET_DLE_TOPO = "routing.key.websocket.dle.topo";
    public static final String MQ_QUEUE_WEBSOCKET_SNMP_TRAP = "queue.websocket.snmp.trap";
    public static final String MQ_QUEUE_WEBSOCKET_TOPO = "queue.websocket.topo";
    public static final String MQ_QUEUE_WEBSOCKET_AE = "queue.websocket.ae";
    public static final String MQ_QUEUE_WEBSOCKET_DLE = "queue.websocket.dle";

    public static final String MQ_EXCHANGE_LOG = "exchange.log";
    public static final String MQ_EXCHANGE_LOG_AE = "exchange.log.ae";
    public static final String MQ_EXCHANGE_LOG_DLE = "exchange.log.dle";
    public static final String MQ_ROUTING_KEY_LOG = "routing.key.log";
    public static final String MQ_ROUTING_KEY_LOG_AE = "routing.key.log.ae";
    public static final String MQ_ROUTING_KEY_LOG_DLE = "routing.key.log.dle";
    public static final String MQ_ROUTING_KEY_LOG_DLE_LOG = "routing.key.log.dle.log";
    public static final String MQ_QUEUE_LOG = "queue.log";
    public static final String MQ_QUEUE_LOG_AE = "queue.log.ae";
    public static final String MQ_QUEUE_LOG_DLE = "queue.log.dle";

    private MqConstants() {
    }

}
