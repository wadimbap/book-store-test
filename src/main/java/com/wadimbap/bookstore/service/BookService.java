package com.wadimbap.bookstore.service;

import com.wadimbap.bookstore.dto.BookDTO;
import com.wadimbap.bookstore.exception.BookNotFoundException;
import com.wadimbap.bookstore.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookDTO saveBook(Book book);

    Optional<BookDTO> getBookById(Long bookId);

    List<BookDTO> getAllBooks();

    List<BookDTO> getFilteredBooks(String title, String isbn, Long authorId);

    BookDTO updateBook(Long bookId, Book book) throws BookNotFoundException;

    void deleteBook(Long bookId) throws BookNotFoundException;
}
