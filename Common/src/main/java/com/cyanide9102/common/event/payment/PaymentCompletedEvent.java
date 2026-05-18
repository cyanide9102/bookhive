package com.cyanide9102.common.event.payment;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentCompletedEvent(

        String trackingId,

        String paymentId,

        String orderId,

        BigDecimal totalAmount,

        String paymentToken,

        String userId,

        Instant occurredAt

) {

    public PaymentCompletedEvent {

        if (occurredAt == null) {

            occurredAt = Instant.now();
        }
    }
}
