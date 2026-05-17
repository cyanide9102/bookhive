package com.cyanide9102.catalogservice.book;

import com.cyanide9102.common.context.UserContext;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "stock_transactions", indexes = {@Index(name = "idx_stock_book_id", columnList = "book_id")})
public class StockTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "book_id")
    private UUID bookId;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private StockTransactionType type;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "created_at")
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();

        String userId = UserContext.getUserId();
        if (userId != null) {
            this.userId = userId;
        }
    }
}
