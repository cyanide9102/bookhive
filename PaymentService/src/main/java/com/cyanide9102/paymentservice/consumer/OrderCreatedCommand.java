package com.cyanide9102.paymentservice.consumer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreatedCommand {

    private String orderId;
    private String userId;
    private String trackingId;

    private BigDecimal totalAmount;

    private String paymentToken;
}
