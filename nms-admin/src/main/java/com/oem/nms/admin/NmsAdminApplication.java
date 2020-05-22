package com.oem.nms.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Seaway John
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.oem.nms.common", "com.oem.nms.admin"})
public class NmsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(NmsAdminApplication.class, args);
    }

}
