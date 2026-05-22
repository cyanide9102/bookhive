package com.cyanide9102.paymentservice.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    @NotBlank
    @Size(min = 13, max = 13)
    private String orderId;

    @NotBlank
    @Size(min = 13, max = 13)
    private String userId;

    @NotBlank
    @Size(min = 13, max = 13)
    private String trackingId;

    @NotNull
    private BigDecimal amount;
}
