package com.cyanide9102.catalogservice.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findByCategoryId(UUID categoryId);

    Optional<Book> findByIsbn(String isbn);

    @Modifying
    @Query("UPDATE Book b SET b.stock_quantity = b.stock_quantity - :quantity WHERE b.id = :id AND b.stock_quantity >= :quantity")
    int reserveStock(@Param("id") UUID id, @Param("quantity") int quantity);

    @Modifying
    @Query("UPDATE Book b SET b.stock_quantity = b.stock_quantity + :quantity WHERE b.id = :id")
    int releaseStock(@Param("id") UUID id, @Param("quantity") int quantity);
}
