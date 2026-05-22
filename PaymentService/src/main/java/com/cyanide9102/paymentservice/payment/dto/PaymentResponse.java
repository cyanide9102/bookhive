package com.cyanide9102.paymentservice.payment.dto;

import com.cyanide9102.paymentservice.payment.PaymentStatus;
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
public class PaymentResponse {

    private String id;
    private String orderId;
    private String userId;
    private String trackingId;
    private BigDecimal amount;
    private PaymentStatus status;
    private Instant processedAt;
}
