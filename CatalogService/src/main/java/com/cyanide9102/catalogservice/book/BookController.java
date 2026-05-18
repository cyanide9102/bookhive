package com.cyanide9102.catalogservice.book;

import com.cyanide9102.catalogservice.book.dto.BookRequest;
import com.cyanide9102.catalogservice.book.dto.BookResponse;
import com.cyanide9102.catalogservice.book.dto.InventoryAdjustmentRequest;
import com.cyanide9102.catalogservice.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse createBook(@RequestBody @Valid BookRequest request) {

        return bookService.createBook(request);
    }

    @GetMapping()
    public List<BookResponse> getBooks(@RequestParam(required = false) String categoryId) {

        if (categoryId != null) {
            return bookService.getBooksByCategoryId(categoryId);
        }

        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable String id) {

        return bookService.getBookById(id);
    }

    @GetMapping("/isbn/{isbn}")
    public BookResponse getBookByIsbn(@PathVariable String isbn) {

        return bookService.getBookByIsbn(isbn);
    }

    @PutMapping("/{id}")
    public BookResponse updateBook(@PathVariable String id, @RequestBody @Valid BookRequest request) {

        return bookService.updateBook(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable String id) {

        bookService.deleteBook(id);
    }

    @PostMapping("/inventory/reserve")
    public List<BookResponse> reserve(@RequestBody @Valid List<InventoryAdjustmentRequest> requests) {

        return bookService.reserveStock(requests);
    }

    @PostMapping("/inventory/release")
    public List<BookResponse> release(@RequestBody @Valid List<InventoryAdjustmentRequest> requests) {

        return bookService.releaseStock(requests);
    }
}
