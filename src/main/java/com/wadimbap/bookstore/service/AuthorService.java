package com.wadimbap.bookstore.service;

import com.wadimbap.bookstore.dto.AuthorDTO;
import com.wadimbap.bookstore.dto.BookDTO;
import com.wadimbap.bookstore.exception.AuthorNotFoundException;
import com.wadimbap.bookstore.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    AuthorDTO saveAuthor(Author author);

    Optional<AuthorDTO> getAuthorById(Long authorId);

    List<BookDTO> getAllAuthorBooks(Long authorId) throws AuthorNotFoundException;

    List<AuthorDTO> getAllAuthors();

    AuthorDTO updateAuthor(Long authorId, Author authorDetails) throws AuthorNotFoundException;

    void deleteAuthor(Long authorId) throws AuthorNotFoundException;
}
