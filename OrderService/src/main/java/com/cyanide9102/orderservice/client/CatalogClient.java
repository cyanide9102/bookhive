package com.cyanide9102.orderservice.client;

import com.cyanide9102.orderservice.client.dto.BookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "catalog-service", fallback = CatalogFallback.class)
public interface CatalogClient {

    @GetMapping("/api/v1/books/{id}")
    BookResponse getBookById(@PathVariable UUID id);

    @PostMapping("/api/v1/books/{id}/reserve")
    void reserveStock(@PathVariable UUID id, @RequestParam int quantity);
}
