package com.cyanide9102.catalogservice.book;

import com.cyanide9102.catalogservice.book.dto.BookRequest;
import com.cyanide9102.catalogservice.book.dto.BookResponse;
import com.cyanide9102.catalogservice.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private static final UUID EMPTY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse createBook(@Valid @RequestBody BookRequest request) {

        return bookService.createBook(request);
    }

    @GetMapping()
    public List<BookResponse> getBooksByCategoryId(@RequestParam(required = false) UUID categoryId) {

        if (categoryId != null && !categoryId.equals(EMPTY_UUID)) {
            return bookService.getBooksByCategoryId(categoryId);
        }

        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable UUID id) {

        return bookService.getBookById(id);
    }

    @GetMapping("/isbn/{isbn}")
    public BookResponse getBookByIsbn(@PathVariable String isbn) {

        return bookService.getBookByIsbn(isbn);
    }

    @PutMapping("/{id}")
    public BookResponse updateBook(@PathVariable UUID id, @Valid @RequestBody BookRequest request) {

        return bookService.updateBook(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable UUID id) {

        bookService.deleteBook(id);
    }

    @PostMapping("/{id}/reserve")
    public void reserve(@PathVariable UUID id, @RequestParam int quantity) {

        bookService.reserveStock(id, quantity);
    }

    @PostMapping("/{id}/release")
    public void release(@PathVariable UUID id, @RequestParam int quantity) {

        bookService.releaseStock(id, quantity);
    }
}
