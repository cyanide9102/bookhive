package com.cyanide9102.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.cyanide9102.orderservice", "com.cyanide9102.common"})
@EnableFeignClients
public class OrderServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
