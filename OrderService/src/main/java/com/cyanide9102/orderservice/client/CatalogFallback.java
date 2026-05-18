package com.cyanide9102.orderservice.client;

import com.cyanide9102.common.exception.ServiceUnavailableException;
import com.cyanide9102.orderservice.client.dto.BookResponse;
import com.cyanide9102.orderservice.client.dto.InventoryAdjustmentRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CatalogFallback implements CatalogClient {

    @Override
    public List<BookResponse> reserveStock(List<InventoryAdjustmentRequest> requests) {

        throw new ServiceUnavailableException("Catalog Service is down. Unable to reserve stock!");
    }
}
