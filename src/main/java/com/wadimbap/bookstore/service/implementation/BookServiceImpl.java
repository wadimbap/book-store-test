package com.wadimbap.bookstore.service.implementation;

import com.wadimbap.bookstore.exception.BookNotFoundException;
import com.wadimbap.bookstore.model.Book;
import com.wadimbap.bookstore.repository.BookRepository;
import com.wadimbap.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getFilteredBooks(String title, String isbn, Long authorId) {
        if (title != null && isbn != null && authorId != null) {
            // Фильтрация по наименованию, ISBN и автору
            return bookRepository.findByTitleContainingAndISBNContainingAndAuthorsId(title, isbn, authorId);
        } else if (title != null && isbn != null) {
            // Фильтрация по наименованию и ISBN
            return bookRepository.findByTitleContainingAndISBNContaining(title, isbn);
        } else if (title != null) {
            // Фильтрация по наименованию
            return bookRepository.findByTitleContaining(title);
        } else if (isbn != null) {
            // Фильтрация по ISBN
            return bookRepository.findByISBNContaining(isbn);
        } else if (authorId != null) {
            // Фильтрация по автору
            return bookRepository.findByAuthorsId(authorId);
        } else {
            // Возвращаем все книги, если фильтры не указаны
            return getAllBooks();
        }
    }

    @Override
    public Book updateBook(Long bookId, Book bookDetails) throws BookNotFoundException {
        Book book = findBookOrThrowException(bookId);
        book.setAuthors(bookDetails.getAuthors());
        book.setTitle(bookDetails.getTitle());
        book.setISBN(bookDetails.getISBN());
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long bookId) throws BookNotFoundException {
        Book book = findBookOrThrowException(bookId);
        bookRepository.delete(book);
    }

    private Book findBookOrThrowException(Long bookId) throws BookNotFoundException {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }
}
