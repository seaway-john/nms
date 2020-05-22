package com.oem.nms.websocket.config;

import com.oem.nms.websocket.interceptor.WebSocketInterceptor;
import com.oem.nms.websocket.util.WebsocketConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/**
 * @author Seaway John
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebSocketInterceptor webSocketInterceptor;

    @Autowired
    public WebSocketConfig(WebSocketInterceptor webSocketInterceptor) {
        this.webSocketInterceptor = webSocketInterceptor;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(WebsocketConstants.WEBSOCKET_ENDPOINT)
                .addInterceptors(webSocketInterceptor)
                .setHandshakeHandler(new DefaultHandshakeHandler())
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(WebsocketConstants.WEBSOCKET_TOPIC_TOPO, WebsocketConstants.WEBSOCKET_TOPIC_SNMP_TRAP);
        registry.setApplicationDestinationPrefixes(WebsocketConstants.WEBSOCKET_CLIENT_SEND_PREFIX);
    }

}
