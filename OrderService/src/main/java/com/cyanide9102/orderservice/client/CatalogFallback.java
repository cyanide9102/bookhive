package com.cyanide9102.orderservice.client;

import com.cyanide9102.orderservice.client.dto.BookResponse;
import com.cyanide9102.orderservice.common.exception.ServiceUnavailableException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CatalogFallback implements CatalogClient {
    @Override
    public BookResponse getBookById(UUID id) {

        throw new ServiceUnavailableException("Catalog Service is down. Unable to fetch book details!");
    }

    @Override
    public void reserveStock(UUID id, int quantity) {

        throw new ServiceUnavailableException("Catalog Service is down. Unable to reserve stock!");
    }
}
