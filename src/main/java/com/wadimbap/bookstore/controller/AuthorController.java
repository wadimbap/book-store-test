package com.wadimbap.bookstore.controller;

import com.wadimbap.bookstore.exception.AuthorNotFoundException;
import com.wadimbap.bookstore.model.Author;
import com.wadimbap.bookstore.model.Book;
import com.wadimbap.bookstore.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/author", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<Optional<Author>> getAuthorById(@PathVariable Long authorId) {
        Optional<Author> author = authorService.getAuthorById(authorId);
        return ResponseEntity.ok(author);
    }

    @GetMapping("/books/{authorId}")
    public ResponseEntity<List<Book>> getAllAuthorBooksById(@PathVariable Long authorId) {
        List<Book> books = authorService.getAllAuthorBooks(authorId);
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public Author saveAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long authorId,
                                               @RequestBody Author authorDetails) throws AuthorNotFoundException {
        Author updatedAuthor = authorService.updateAuthor(authorId, authorDetails);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Long> deleteAuthor(@PathVariable Long authorId) throws AuthorNotFoundException {
        authorService.deleteAuthor(authorId);
        return ResponseEntity.ok(authorId);
    }
}
