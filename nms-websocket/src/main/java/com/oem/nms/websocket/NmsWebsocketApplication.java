package com.oem.nms.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Seaway John
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.oem.nms.common", "com.oem.nms.websocket"})
public class NmsWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(NmsWebsocketApplication.class, args);
    }

}
