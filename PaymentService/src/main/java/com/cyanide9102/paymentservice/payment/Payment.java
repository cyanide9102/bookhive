package com.cyanide9102.paymentservice.payment;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @Column(length = 13, columnDefinition = "char(13)")
    private String id;

    @Column(name = "order_id", nullable = false, length = 13, columnDefinition = "char(13)")
    private String orderId;

    @Column(name = "user_id", length = 13, columnDefinition = "char(13)")
    private String userId;

    @Column(name = "tracking_id", nullable = false, unique = true, length = 13, columnDefinition = "char(13)")
    private String trackingId;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(name = "processed_at")
    private Instant processedAt;

    @PrePersist
    public void prePersist() {

        this.processedAt = Instant.now();
    }
}
