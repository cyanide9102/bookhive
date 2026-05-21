package com.cyanide9102.common.event.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCompletedEvent {


    private String paymentId;
    private String orderId;
    private String userId;
    private String trackingId;

    private BigDecimal totalAmount;

    @Builder.Default
    private Instant occurredAt = Instant.now();
}
