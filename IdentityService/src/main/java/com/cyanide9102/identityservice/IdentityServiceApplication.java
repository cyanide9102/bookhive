package com.cyanide9102.identityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.cyanide9102.identityservice", "com.cyanide9102.common"})
public class IdentityServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(IdentityServiceApplication.class, args);
    }
}
