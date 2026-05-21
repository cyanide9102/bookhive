package com.cyanide9102.catalogservice.book.service;

import com.cyanide9102.catalogservice.book.dto.BookRequest;
import com.cyanide9102.catalogservice.book.dto.BookResponse;
import com.cyanide9102.catalogservice.book.dto.InventoryAdjustmentRequest;

import java.util.List;

public interface BookService {

    BookResponse createBook(BookRequest request, String userId);

    List<BookResponse> getBooks();

    List<BookResponse> getBooksByCategoryId(String categoryId);

    BookResponse getBookById(String id);

    BookResponse getBookByIsbn(String isbn);

    BookResponse updateBook(String id, BookRequest request, String userId);

    void deleteBook(String id);

    List<BookResponse> reserveStock(List<InventoryAdjustmentRequest> requests, String userId);

    List<BookResponse> releaseStock(List<InventoryAdjustmentRequest> requests, String userId);
}
