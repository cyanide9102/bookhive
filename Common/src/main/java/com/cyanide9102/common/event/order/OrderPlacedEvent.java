package com.cyanide9102.common.event.order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderPlacedEvent(

        String trackingId,

        String orderId,

        BigDecimal totalAmount,

        String paymentToken,

        String userId,

        List<SharedEventBook> items,

        Instant occurredAt

) {

    public OrderPlacedEvent {

        if (occurredAt == null) {

            occurredAt = Instant.now();
        }
    }
}
