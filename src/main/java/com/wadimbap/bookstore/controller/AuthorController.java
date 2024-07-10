package com.wadimbap.bookstore.controller;

import com.wadimbap.bookstore.dto.AuthorDTO;
import com.wadimbap.bookstore.dto.BookDTO;
import com.wadimbap.bookstore.exception.AuthorNotFoundException;
import com.wadimbap.bookstore.model.Author;
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
    public List<AuthorDTO> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<Optional<AuthorDTO>> getAuthorById(@PathVariable Long authorId) {
        Optional<AuthorDTO> author = authorService.getAuthorById(authorId);
        return ResponseEntity.ok(author);
    }

    @GetMapping("/books/{authorId}")
    public ResponseEntity<List<BookDTO>> getAllAuthorBooksById(@PathVariable Long authorId)
            throws AuthorNotFoundException {
        List<BookDTO> books = authorService.getAllAuthorBooks(authorId);
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public AuthorDTO saveAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long authorId,
                                                  @RequestBody Author authorDetails)
            throws AuthorNotFoundException {
        AuthorDTO updatedAuthor = authorService.updateAuthor(authorId, authorDetails);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Long> deleteAuthor(@PathVariable Long authorId)
            throws AuthorNotFoundException {
        authorService.deleteAuthor(authorId);
        return ResponseEntity.ok(authorId);
    }
}
