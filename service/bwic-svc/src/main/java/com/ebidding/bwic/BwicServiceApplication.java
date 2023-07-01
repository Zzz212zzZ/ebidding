package com.ebidding.bwic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
public class BwicServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BwicServiceApplication.class, args);
    }
}
