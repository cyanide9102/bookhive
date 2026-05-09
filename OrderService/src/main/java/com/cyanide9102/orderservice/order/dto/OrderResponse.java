package com.cyanide9102.orderservice.order.dto;

import com.cyanide9102.orderservice.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private UUID id;
    private UUID bookId;
    private Short quantity;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private Instant createdAt;
}
