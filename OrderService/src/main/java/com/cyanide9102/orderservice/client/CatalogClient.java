package com.cyanide9102.orderservice.client;

import com.cyanide9102.orderservice.client.dto.BookResponse;
import com.cyanide9102.orderservice.client.dto.InventoryAdjustmentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "catalog-service", fallback = CatalogFallback.class)
public interface CatalogClient {

    @PostMapping("/api/v1/books/inventory/reserve")
    List<BookResponse> reserveStock(@RequestBody List<InventoryAdjustmentRequest> requests);
}
