package com.cyanide9102.catalogservice.book;

import com.cyanide9102.catalogservice.common.EntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "stock_transactions", indexes = {@Index(name = "idx_stock_book_id", columnList = "book_id")})
public class StockTransaction extends EntityBase {

    @Column(name = "book_id")
    private String bookId;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private StockTransactionType type;
}
