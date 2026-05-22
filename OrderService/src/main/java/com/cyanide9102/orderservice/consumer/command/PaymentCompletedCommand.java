package com.cyanide9102.orderservice.consumer.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentCompletedCommand {

    private String paymentId;
    private String orderId;
    private String userId;
    private String trackingId;
}
