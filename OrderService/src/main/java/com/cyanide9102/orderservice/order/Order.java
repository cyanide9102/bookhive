package com.cyanide9102.orderservice.order;

import com.cyanide9102.orderservice.order.item.OrderItem;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(length = 13, columnDefinition = "char(13)")
    private String id;

    @Column(name = "total_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "user_id", length = 13, columnDefinition = "char(13)")
    private String userId;

    @Column(name = "tracking_id", nullable = false, unique = true, length = 13, columnDefinition = "char(13)")
    private String trackingId;

    @Column(name = "created_at")
    private Instant createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @PrePersist
    public void prePersist() {

        if (this.id == null) {
            this.id = TSID.Factory.getTsid().toString();
        }

        this.createdAt = Instant.now();
    }
}
