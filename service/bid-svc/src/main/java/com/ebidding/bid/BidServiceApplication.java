package com.ebidding.bid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.ebidding.account", "com.ebidding.bwic"})
public class BidServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BidServiceApplication.class, args);
    }
}
