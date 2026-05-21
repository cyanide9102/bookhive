package com.cyanide9102.common.event.order;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderCreatedEvent {

    private String trackingId;

    private String orderId;
    private BigDecimal totalAmount;
    private String userId;
    private List<SharedEventBook> items;

    private String paymentToken;

    @Builder.Default
    private Instant occurredAt = Instant.now();
}
