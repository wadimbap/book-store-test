package com.wadimbap.bookstore.service.implementation;

import com.wadimbap.bookstore.exception.BookNotFoundException;
import com.wadimbap.bookstore.model.Book;
import com.wadimbap.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private List<Book> mockBooks;

    @BeforeEach
    void setUp() {
        // Инициализация списка книг для тестирования
        mockBooks = new ArrayList<>();
        mockBooks.add(new Book(1L, "Book 1", "1234567890", null));
        mockBooks.add(new Book(2L, "Book 2", "0987654321", null));
    }

    @Test
    void getAllBooks_shouldReturnAllBooks() {
        // Arrange
        when(bookRepository.findAll()).thenReturn(mockBooks);

        // Act
        List<Book> allBooks = bookService.getAllBooks();

        // Assert
        assertEquals(mockBooks.size(), allBooks.size());
        assertEquals(mockBooks, allBooks);
    }

    @Test
    void getBookById_shouldReturnBook_whenBookExists() {
        // Arrange
        Long bookId = 1L;
        Book expectedBook = mockBooks.get(0);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(expectedBook));

        // Act
        Optional<Book> foundBook = bookService.getBookById(bookId);

        // Assert
        assertTrue(foundBook.isPresent());
        assertEquals(expectedBook, foundBook.get());
    }

    @Test
    void getBookById_shouldReturnEmptyOptional_whenBookDoesNotExist() {
        // Arrange
        Long bookId = 99L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act
        Optional<Book> foundBook = bookService.getBookById(bookId);

        // Assert
        assertFalse(foundBook.isPresent());
    }

    @Test
    void saveBook_shouldReturnSavedBook() {
        // Arrange
        Book newBook = new Book(null, "New Book", "1112223334", null);
        when(bookRepository.save(newBook)).thenReturn(newBook);

        // Act
        Book savedBook = bookService.saveBook(newBook);

        // Assert
        assertNotNull(savedBook);
        assertEquals(newBook.getTitle(), savedBook.getTitle());
        assertEquals(newBook.getISBN(), savedBook.getISBN());
    }

    @Test
    void updateBook_shouldUpdateBookDetails_whenBookExists() throws BookNotFoundException {
        // Arrange
        Long bookId = 1L;
        Book existingBook = mockBooks.get(0);
        Book updatedBookDetails = new Book(null, "Updated Book", "9998887776", null);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        // Act
        Book updatedBook = bookService.updateBook(bookId, updatedBookDetails);

        // Assert
        assertNotNull(updatedBook);
        assertEquals(updatedBookDetails.getTitle(), updatedBook.getTitle());
        assertEquals(updatedBookDetails.getISBN(), updatedBook.getISBN());
    }

    @Test
    void updateBook_shouldThrowBookNotFoundException_whenBookDoesNotExist() {
        // Arrange
        Long bookId = 99L;
        Book updatedBookDetails = new Book(null, "Updated Book", "9998887776", null);
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(bookId, updatedBookDetails));
    }

    @Test
    void deleteBook_shouldDeleteBook_whenBookExists() throws BookNotFoundException {
        // Arrange
        Long bookId = 1L;
        Book existingBook = mockBooks.get(0);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        // Act
        bookService.deleteBook(bookId);

        // Assert
        verify(bookRepository, times(1)).delete(existingBook);
    }

    @Test
    void deleteBook_shouldThrowBookNotFoundException_whenBookDoesNotExist() {
        // Arrange
        Long bookId = 99L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(bookId));
    }
}
