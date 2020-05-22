package com.oem.nms.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Seaway John
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.oem.nms.common", "com.oem.nms.manager"})
public class NmsManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NmsManagerApplication.class, args);
    }

}
