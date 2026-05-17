package com.cyanide9102.catalogservice.book.service.impl;

import com.cyanide9102.catalogservice.book.*;
import com.cyanide9102.catalogservice.book.dto.BookRequest;
import com.cyanide9102.catalogservice.book.dto.BookResponse;
import com.cyanide9102.catalogservice.book.service.BookService;
import com.cyanide9102.catalogservice.category.Category;
import com.cyanide9102.catalogservice.category.CategoryRepository;
import com.cyanide9102.common.annotation.RequiresAdmin;
import com.cyanide9102.common.annotation.RequiresLogin;
import com.cyanide9102.common.exception.InsufficientStockException;
import com.cyanide9102.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final StockTransactionRepository stockTransactionRepository;
    private final CategoryRepository categoryRepository;

    private final BookMapper bookMapper;

    @RequiresAdmin
    @Transactional
    @Override
    public BookResponse createBook(BookRequest request) {

        Category category = getCategory(request.getCategoryId());

        Book book = bookMapper.toEntity(request, category);
        book = bookRepository.save(book);

        return bookMapper.fromEntity(book);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookResponse> getBooks() {

        List<Book> books = bookRepository.findAll();
        return books.stream().map(bookMapper::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookResponse> getBooksByCategoryId(UUID categoryId) {

        List<Book> books = bookRepository.findByCategoryId(categoryId);
        return books.stream().map(bookMapper::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public BookResponse getBookById(UUID id) {

        Book book = getBook(id);
        return bookMapper.fromEntity(book);
    }

    @Transactional(readOnly = true)
    @Override
    public BookResponse getBookByIsbn(String isbn) {

        Book book = getBook(isbn);
        return bookMapper.fromEntity(book);
    }

    @RequiresAdmin
    @Transactional
    @Override
    public BookResponse updateBook(UUID id, BookRequest request) {

        Category category = getCategory(request.getCategoryId());

        Book book = getBook(id);

        bookMapper.updateEntity(book, request, category);
        book = bookRepository.save(book);

        return bookMapper.fromEntity(book);
    }

    @RequiresAdmin
    @Transactional
    @Override
    public void deleteBook(UUID id) {

        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(bookRepository::delete);
    }

    @RequiresLogin
    @Transactional
    @Override
    public void reserveStock(UUID id, int quantity) {

        int rowsUpdated = bookRepository.reserveStock(id, quantity);
        if (rowsUpdated == 0) {
            Book book = getBook(id);
            throw new InsufficientStockException("Not enough stock was available at the time of your request!", book.getId().toString(), quantity, book.getStockQuantity());
        }

        StockTransaction log = StockTransaction.builder().bookId(id).quantity(-quantity).type(StockTransactionType.RESERVE).build();
        stockTransactionRepository.save(log);
    }

    @RequiresLogin
    @Transactional
    @Override
    public void releaseStock(UUID id, int quantity) {

        int rowsUpdated = bookRepository.releaseStock(id, quantity);
        if (rowsUpdated > 0) {
            StockTransaction log = StockTransaction.builder().bookId(id).quantity(+quantity).type(StockTransactionType.RELEASE).build();
            stockTransactionRepository.save(log);
        }
    }

    private Category getCategory(UUID id) {

        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found!", Category.class.getSimpleName(), id.toString()));
    }

    private Book getBook(UUID id) {

        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found!", Book.class.getSimpleName(), id.toString()));
    }

    private Book getBook(String isbn) {

        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new ResourceNotFoundException("Book not found!", Book.class.getSimpleName(), isbn));
    }
}
