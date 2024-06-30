package com.wadimbap.bookstore.service.implementation;

import com.wadimbap.bookstore.exception.AuthorNotFoundException;
import com.wadimbap.bookstore.model.Author;
import com.wadimbap.bookstore.model.Book;
import com.wadimbap.bookstore.repository.AuthorRepository;
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
public class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private List<Author> mockAuthors;
    private List<Book> mockBooks;

    @BeforeEach
    void setUp() {
        // Инициализация списка авторов для тестирования
        mockAuthors = new ArrayList<>();
        mockAuthors.add(new Author(1L, "Author 1", null));
        mockAuthors.add(new Author(2L, "Author 2", null));

        // Инициализация списка книг для тестирования
        mockBooks = new ArrayList<>();
        mockBooks.add(new Book(1L, "Book 1", "1234567890", null));
        mockBooks.add(new Book(2L, "Book 2", "0987654321", null));
    }

    @Test
    void getAllAuthors_shouldReturnAllAuthors() {
        // Arrange
        when(authorRepository.findAll()).thenReturn(mockAuthors);

        // Act
        List<Author> allAuthors = authorService.getAllAuthors();

        // Assert
        assertEquals(mockAuthors.size(), allAuthors.size());
        assertEquals(mockAuthors, allAuthors);
    }

    @Test
    void getAuthorById_shouldReturnAuthor_whenAuthorExists() {
        // Arrange
        Long authorId = 1L;
        Author expectedAuthor = mockAuthors.get(0);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(expectedAuthor));

        // Act
        Optional<Author> foundAuthor = authorService.getAuthorById(authorId);

        // Assert
        assertTrue(foundAuthor.isPresent());
        assertEquals(expectedAuthor, foundAuthor.get());
    }

    @Test
    void getAuthorById_shouldReturnEmptyOptional_whenAuthorDoesNotExist() {
        // Arrange
        Long authorId = 99L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // Act
        Optional<Author> foundAuthor = authorService.getAuthorById(authorId);

        // Assert
        assertFalse(foundAuthor.isPresent());
    }

    @Test
    void saveAuthor_shouldReturnSavedAuthor() {
        // Arrange
        Author newAuthor = new Author(null, "New Author", null);
        when(authorRepository.save(newAuthor)).thenReturn(newAuthor);

        // Act
        Author savedAuthor = authorService.saveAuthor(newAuthor);

        // Assert
        assertNotNull(savedAuthor);
        assertEquals(newAuthor.getName(), savedAuthor.getName());
    }

    @Test
    void updateAuthor_shouldUpdateAuthorDetails_whenAuthorExists() throws AuthorNotFoundException {
        // Arrange
        Long authorId = 1L;
        Author existingAuthor = mockAuthors.get(0);
        Author updatedAuthorDetails = new Author(null, "Updated Author", null);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(existingAuthor)).thenReturn(existingAuthor);

        // Act
        Author updatedAuthor = authorService.updateAuthor(authorId, updatedAuthorDetails);

        // Assert
        assertNotNull(updatedAuthor);
        assertEquals(updatedAuthorDetails.getName(), updatedAuthor.getName());
    }

    @Test
    void updateAuthor_shouldThrowAuthorNotFoundException_whenAuthorDoesNotExist() {
        // Arrange
        Long authorId = 99L;
        Author updatedAuthorDetails = new Author(null, "Updated Author", null);
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AuthorNotFoundException.class, () -> authorService.updateAuthor(authorId, updatedAuthorDetails));
    }

    @Test
    void deleteAuthor_shouldDeleteAuthor_whenAuthorExists() throws AuthorNotFoundException {
        // Arrange
        Long authorId = 1L;
        Author existingAuthor = mockAuthors.get(0);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));

        // Act
        authorService.deleteAuthor(authorId);

        // Assert
        verify(authorRepository, times(1)).delete(existingAuthor);
    }

    @Test
    void deleteAuthor_shouldThrowAuthorNotFoundException_whenAuthorDoesNotExist() {
        // Arrange
        Long authorId = 99L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AuthorNotFoundException.class, () -> authorService.deleteAuthor(authorId));
    }

    @Test
    void getAllAuthorBooks_shouldReturnBooks_whenAuthorExists() {
        // Arrange
        Long authorId = 1L;
        when(authorRepository.getAuthorBooks(authorId)).thenReturn(mockBooks);

        // Act
        List<Book> authorBooks = authorService.getAllAuthorBooks(authorId);

        // Assert
        assertEquals(mockBooks.size(), authorBooks.size());
        assertEquals(mockBooks, authorBooks);
    }
}
