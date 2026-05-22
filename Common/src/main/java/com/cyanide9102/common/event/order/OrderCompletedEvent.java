package com.cyanide9102.common.event.order;

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
public class OrderCompletedEvent {

    private String paymentId;
    private String orderId;
    private String userId;
    private String trackingId;

    private BigDecimal totalAmount;

    private List<SharedOrderItem> items;

    @Builder.Default
    private Instant occurredAt = Instant.now();
}
