package com.cyanide9102.catalogservice.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findByCategoryId(String categoryId);

    Optional<Book> findByIsbn(String isbn);

    @Modifying
    @Query("UPDATE Book b SET b.stockQuantity = b.stockQuantity - :quantity WHERE b.id = :id AND b.stockQuantity >= :quantity")
    int reserveStock(@Param("id") String id, @Param("quantity") int quantity);

    @Modifying
    @Query("UPDATE Book b SET b.stockQuantity = b.stockQuantity + :quantity WHERE b.id = :id")
    int releaseStock(@Param("id") String id, @Param("quantity") int quantity);
}
