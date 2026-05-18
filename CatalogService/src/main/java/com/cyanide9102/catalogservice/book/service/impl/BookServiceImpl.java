package com.cyanide9102.catalogservice.book.service.impl;

import com.cyanide9102.catalogservice.book.*;
import com.cyanide9102.catalogservice.book.dto.BookRequest;
import com.cyanide9102.catalogservice.book.dto.BookResponse;
import com.cyanide9102.catalogservice.book.dto.InventoryAdjustmentRequest;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<BookResponse> getBooksByCategoryId(String categoryId) {

        List<Book> books = bookRepository.findByCategoryId(categoryId);
        return books.stream().map(bookMapper::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public BookResponse getBookById(String id) {

        Book book = fetchBook(id);
        return bookMapper.fromEntity(book);
    }

    @Transactional(readOnly = true)
    @Override
    public BookResponse getBookByIsbn(String isbn) {

        Book book = fetchBookByIsbn(isbn);
        return bookMapper.fromEntity(book);
    }

    @RequiresAdmin
    @Transactional
    @Override
    public BookResponse updateBook(String id, BookRequest request) {

        Category category = getCategory(request.getCategoryId());

        Book book = fetchBook(id);

        bookMapper.updateEntity(book, request, category);
        book = bookRepository.save(book);

        return bookMapper.fromEntity(book);
    }

    @RequiresAdmin
    @Transactional
    @Override
    public void deleteBook(String id) {

        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(bookRepository::delete);
    }

    @RequiresLogin
    @Transactional
    @Override
    public List<BookResponse> reserveStock(List<InventoryAdjustmentRequest> requests) {

        List<Book> books = new ArrayList<>();

        for (InventoryAdjustmentRequest request : requests) {
            int rowsUpdated = bookRepository.reserveStock(request.getBookId(), request.getQuantity());
            if (rowsUpdated == 0) {
                Book book = fetchBook(request.getBookId());
                throw new InsufficientStockException("Not enough stock was available at the time of your request!", book.getId(), request.getQuantity(), book.getStockQuantity());
            }

            StockTransaction log = StockTransaction.builder().bookId(request.getBookId()).quantity(-request.getQuantity()).type(StockTransactionType.RESERVE).build();
            stockTransactionRepository.save(log);

            Book book = fetchBook(request.getBookId());
            books.add(book);
        }

        return books.stream().map(bookMapper::fromEntity).toList();
    }

    @RequiresLogin
    @Transactional
    @Override
    public List<BookResponse> releaseStock(List<InventoryAdjustmentRequest> requests) {

        List<Book> books = new ArrayList<>();

        for (InventoryAdjustmentRequest request : requests) {
            int rowsUpdated = bookRepository.releaseStock(request.getBookId(), request.getQuantity());
            if (rowsUpdated == 0) {
                throw new ResourceNotFoundException("Book not found!", Book.class.getSimpleName(), request.getBookId());
            }

            StockTransaction log = StockTransaction.builder().bookId(request.getBookId()).quantity(+request.getQuantity()).type(StockTransactionType.RELEASE).build();
            stockTransactionRepository.save(log);

            Book book = fetchBook(request.getBookId());
            books.add(book);
        }

        return books.stream().map(bookMapper::fromEntity).toList();
    }

    private Category getCategory(String id) {

        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found!", Category.class.getSimpleName(), id));
    }

    private Book fetchBook(String id) {

        return bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found!", Book.class.getSimpleName(), id));
    }

    private Book fetchBookByIsbn(String isbn) {

        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new ResourceNotFoundException("Book not found!", Book.class.getSimpleName(), isbn));
    }
}
