package com.wadimbap.bookstore.service;

import com.wadimbap.bookstore.exception.AuthorNotFoundException;
import com.wadimbap.bookstore.model.Author;
import com.wadimbap.bookstore.model.Book;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author saveAuthor(Author author);

    Optional<Author> getAuthorById(Long authorId);

    List<Book> getAllAuthorBooks(Long authorId);

    List<Author> getAllAuthors();

    Author updateAuthor(Long authorId, Author authorDetails) throws AuthorNotFoundException;

    void deleteAuthor(Long authorId) throws AuthorNotFoundException;
}
