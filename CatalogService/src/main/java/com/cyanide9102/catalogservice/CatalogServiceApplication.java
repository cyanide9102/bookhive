package com.cyanide9102.catalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.cyanide9102.catalogservice", "com.cyanide9102.common"})
public class CatalogServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(CatalogServiceApplication.class, args);
    }
}
