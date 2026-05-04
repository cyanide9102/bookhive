package com.cyanide9102.catalogservice.book.repository;

import com.cyanide9102.catalogservice.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByCategoryId(UUID categoryId);
    Optional<Book> findByIsbn(String isbn);
}
