package com.cyanide9102.orderservice.order.item;

import com.cyanide9102.orderservice.order.Order;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @Column(length = 13, columnDefinition = "char(13)")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, columnDefinition = "char(13)")
    private Order order;

    @Column(name = "book_id", nullable = false, length = 13, columnDefinition = "char(13)")
    private String bookId;

    @Column(name = "book_title", nullable = false)
    private String bookTitle;

    @Column(nullable = false, columnDefinition = "smallint check (quantity >= 0)")
    private Short quantity;

    @Column(nullable = false, precision = 12, scale = 2, columnDefinition = "decimal(12,2) check (price > 0)")
    private BigDecimal price;

    @PrePersist
    public void prePersist() {

        if (this.id == null) {
            this.id = TSID.Factory.getTsid().toString();
        }
    }
}
