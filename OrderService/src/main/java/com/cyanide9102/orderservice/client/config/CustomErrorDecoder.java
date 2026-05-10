package com.cyanide9102.orderservice.client.config;

import com.cyanide9102.common.exception.InsufficientStockException;
import com.cyanide9102.common.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.body() == null) {
            return defaultDecoder.decode(methodKey, response);
        }

        try {
            Map<String, Object> errorMap = objectMapper.readValue(response.body().asInputStream(), new TypeReference<>() {
            });

            return switch (response.status()) {
                case 404 ->
                        new ResourceNotFoundException((String) errorMap.get("error"), (String) errorMap.get("resourceType"), (String) errorMap.get("resourceId"));
                case 409 ->
                        new InsufficientStockException((String) errorMap.get("error"), (String) errorMap.get("bookId"), (Integer) errorMap.get("requested"), (Integer) errorMap.get("available"));
                default -> defaultDecoder.decode(methodKey, response);
            };
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return defaultDecoder.decode(methodKey, response);
    }
}
