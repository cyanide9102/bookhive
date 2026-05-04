package com.cyanide9102.catalogservice.book.service;

import com.cyanide9102.catalogservice.book.dto.BookRequest;
import com.cyanide9102.catalogservice.book.dto.BookResponse;

import java.util.List;
import java.util.UUID;

public interface BookService {

    BookResponse createBook(BookRequest request);

    List<BookResponse> getBooks();

    List<BookResponse> getBooksByCategoryId(UUID categoryId);

    BookResponse getBookById(UUID id);

    BookResponse getBookByIsbn(String isbn);

    BookResponse updateBook(UUID id, BookRequest request);

    void deleteBook(UUID id);
}
