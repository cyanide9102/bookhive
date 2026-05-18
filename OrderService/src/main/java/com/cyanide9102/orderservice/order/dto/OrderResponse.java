package com.cyanide9102.orderservice.order.dto;

import com.cyanide9102.orderservice.order.OrderStatus;
import com.cyanide9102.orderservice.order.item.dto.OrderItemResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private String id;
    private String trackingId;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private String userId;
    private Instant createdAt;
    private List<OrderItemResponse> items;
}
