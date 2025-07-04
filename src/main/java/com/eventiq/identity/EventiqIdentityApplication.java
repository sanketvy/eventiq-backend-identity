package com.eventiq.identity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EventiqIdentityApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventiqIdentityApplication.class, args);
    }

}
