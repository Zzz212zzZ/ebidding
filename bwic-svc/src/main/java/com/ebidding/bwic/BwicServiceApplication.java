package com.ebidding.bwic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.ebidding.bid.api"})
public class BwicServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BwicServiceApplication.class, args);
    }
}
