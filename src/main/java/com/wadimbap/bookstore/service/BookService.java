package com.wadimbap.bookstore.service;

import com.wadimbap.bookstore.exception.BookNotFoundException;
import com.wadimbap.bookstore.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(Book book);

    Optional<Book> getBookById(Long bookId);

    List<Book> getAllBooks();

    List<Book> getFilteredBooks(String title, String isbn, Long authorId);

    Book updateBook(Long bookId, Book book) throws BookNotFoundException;

    void deleteBook(Long bookId) throws BookNotFoundException;
}
