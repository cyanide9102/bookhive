package com.cyanide9102.catalogservice.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, UUID> {
}
