package com.oem.nms.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @author Seaway John
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.oem.nms.common", "com.oem.nms.gateway"})
public class NmsGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(NmsGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes().build();
    }

}
