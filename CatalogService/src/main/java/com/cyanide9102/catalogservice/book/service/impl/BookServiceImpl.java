package com.cyanide9102.catalogservice.book.service.impl;

import com.cyanide9102.catalogservice.book.Book;
import com.cyanide9102.catalogservice.book.BookMapper;
import com.cyanide9102.catalogservice.book.BookRepository;
import com.cyanide9102.catalogservice.book.dto.BookRequest;
import com.cyanide9102.catalogservice.book.dto.BookResponse;
import com.cyanide9102.catalogservice.book.service.BookService;
import com.cyanide9102.catalogservice.category.Category;
import com.cyanide9102.catalogservice.category.CategoryRepository;
import com.cyanide9102.catalogservice.common.exception.ResourceNotFoundException;
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
    private final BookMapper bookMapper;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public BookResponse createBook(BookRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category with id " + request.getCategoryId() + " not found!"));

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

        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found!"));
        return bookMapper.fromEntity(book);
    }

    @Transactional(readOnly = true)
    @Override
    public BookResponse getBookByIsbn(String isbn) {

        Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new ResourceNotFoundException("Book with id " + isbn + " not found!"));
        return bookMapper.fromEntity(book);
    }

    @Transactional
    @Override
    public BookResponse updateBook(UUID id, BookRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category with id " + request.getCategoryId() + " not found!"));

        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found!"));
        bookMapper.updateEntity(book, request, category);
        book = bookRepository.save(book);

        return bookMapper.fromEntity(book);
    }

    @Transactional
    @Override
    public void deleteBook(UUID id) {

        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(bookRepository::delete);
    }
}
