package com.wadimbap.bookstore.service.implementation;

import com.wadimbap.bookstore.converter.DTOConverter;
import com.wadimbap.bookstore.dto.BookDTO;
import com.wadimbap.bookstore.exception.BookNotFoundException;
import com.wadimbap.bookstore.model.Book;
import com.wadimbap.bookstore.repository.BookRepository;
import com.wadimbap.bookstore.service.BookService;
import com.wadimbap.bookstore.specification.BookSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final DTOConverter dtoConverter;

    @Override
    public BookDTO saveBook(Book book) {
        Book savedBook = bookRepository.save(book);
        return dtoConverter.convertToBookDTO(savedBook);
    }

    @Override
    public Optional<BookDTO> getBookById(Long bookId) {
        return bookRepository.findById(bookId).map(dtoConverter::convertToBookDTO);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(dtoConverter::convertToBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getFilteredBooks(String title, String isbn, Long authorId) {
        return bookRepository.findAll(
                Specification
                        .where(BookSpecification.hasTitle(title))
                        .and(BookSpecification.hasISBN(isbn))
                        .and(BookSpecification.hasAuthorId(authorId))
                )
                .stream()
                .map(dtoConverter::convertToBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO updateBook(Long bookId, Book bookDetails) throws BookNotFoundException {
        Book book = findBookOrThrowException(bookId);
        book.setAuthors(bookDetails.getAuthors());
        book.setTitle(bookDetails.getTitle());
        book.setISBN(bookDetails.getISBN());
        Book updatedBook = bookRepository.save(book);
        return dtoConverter.convertToBookDTO(updatedBook);
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
