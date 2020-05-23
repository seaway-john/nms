package com.oem.nms.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Seaway John
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.oem.nms.common", "com.oem.nms.log"})
public class NmsLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(NmsLogApplication.class, args);
    }

}
