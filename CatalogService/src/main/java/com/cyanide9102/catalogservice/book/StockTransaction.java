package com.cyanide9102.catalogservice.book;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "stock_transactions", indexes = {@Index(name = "idx_stock_book_id", columnList = "book_id")})
public class StockTransaction {

    @Id
    @Column(length = 13, columnDefinition = "char(13)")
    private String id;

    @Column(name = "book_id", length = 13, columnDefinition = "char(13)")
    private String bookId;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private StockTransactionType type;

    @Column(name = "user_id", length = 13, columnDefinition = "char(13)")
    private String userId;

    @Column(name = "created_at")
    private Instant createdAt;

    @PrePersist
    public void prePersist() {

        if (id == null) {
            id = TSID.Factory.getTsid().toString();
        }

        this.createdAt = Instant.now();
    }
}
