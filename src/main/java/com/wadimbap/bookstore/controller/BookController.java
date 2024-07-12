package com.wadimbap.bookstore.controller;

import com.wadimbap.bookstore.dto.BookDTO;
import com.wadimbap.bookstore.exception.BookNotFoundException;
import com.wadimbap.bookstore.model.Book;
import com.wadimbap.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/book", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Optional<BookDTO>> getBookById(@PathVariable Long bookId) {
        Optional<BookDTO> book = bookService.getBookById(bookId);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/filter")
    public List<BookDTO> getFilteredBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Long authorId) {
        return bookService.getFilteredBooks(title, isbn, authorId);
    }

    @PostMapping
    public ResponseEntity<BookDTO> saveBook(@RequestBody Book book) {
        BookDTO savedBook = bookService.saveBook(book);
        return ResponseEntity.ok(savedBook);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long bookId,
                                              @RequestBody Book bookDetails)
            throws BookNotFoundException {
        BookDTO updatedBook = bookService.updateBook(bookId, bookDetails);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Long> deleteBook(@PathVariable Long bookId)
            throws BookNotFoundException {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok(bookId);
    }
}
