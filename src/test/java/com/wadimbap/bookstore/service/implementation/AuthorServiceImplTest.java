package com.wadimbap.bookstore.service.implementation;

import com.wadimbap.bookstore.converter.DTOConverter;
import com.wadimbap.bookstore.dto.AuthorDTO;
import com.wadimbap.bookstore.dto.BookDTO;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private DTOConverter dtoConverter;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private List<Author> mockAuthors;
    private List<Book> mockBooks;
    private List<AuthorDTO> mockAuthorDTOs;

    @BeforeEach
    void setUp() {
        // Инициализация списка авторов и их DTO для тестирования
        mockAuthors = new ArrayList<>();
        mockAuthors.add(new Author(1L, "Author 1", new ArrayList<>()));
        mockAuthors.add(new Author(2L, "Author 2", new ArrayList<>()));

        mockBooks = new ArrayList<>();
        mockBooks.add(new Book(1L, "Book 1", "1234567890", new ArrayList<>()));
        mockBooks.add(new Book(2L, "Book 2", "0987654321", new ArrayList<>()));

        mockAuthorDTOs = mockAuthors.stream()
                .map(author -> new AuthorDTO(author.getId(), author.getName(), new ArrayList<>()))
                .collect(Collectors.toList());
    }

    @Test
    void getAllAuthors_shouldReturnAllAuthors() {
        // Arrange
        when(authorRepository.findAll()).thenReturn(mockAuthors);
        when(dtoConverter.convertToAuthorDTO(any(Author.class))).thenReturn(mockAuthorDTOs.get(0));

        // Act
        List<AuthorDTO> allAuthors = authorService.getAllAuthors();

        // Assert
        assertEquals(mockAuthors.size(), allAuthors.size());
    }

    @Test
    void getAuthorById_shouldReturnAuthor_whenAuthorExists() {
        // Arrange
        Long authorId = 1L;
        Author expectedAuthor = mockAuthors.get(0);
        AuthorDTO expectedAuthorDTO = mockAuthorDTOs.get(0);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(expectedAuthor));
        when(dtoConverter.convertToAuthorDTO(expectedAuthor)).thenReturn(expectedAuthorDTO);

        // Act
        Optional<AuthorDTO> foundAuthor = authorService.getAuthorById(authorId);

        // Assert
        assertTrue(foundAuthor.isPresent());
        assertEquals(expectedAuthorDTO, foundAuthor.get());
    }

    @Test
    void getAuthorById_shouldReturnEmptyOptional_whenAuthorDoesNotExist() {
        // Arrange
        Long authorId = 99L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        // Act
        Optional<AuthorDTO> foundAuthor = authorService.getAuthorById(authorId);

        // Assert
        assertFalse(foundAuthor.isPresent());
    }

    @Test
    void saveAuthor_shouldReturnSavedAuthor() {
        // Arrange
        Author newAuthor = new Author(null, "New Author", null);
        AuthorDTO expectedAuthorDTO = new AuthorDTO(null, "New Author", null);
        when(authorRepository.save(newAuthor)).thenReturn(newAuthor);
        when(dtoConverter.convertToAuthorDTO(newAuthor)).thenReturn(expectedAuthorDTO);

        // Act
        AuthorDTO savedAuthor = authorService.saveAuthor(newAuthor);

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
        AuthorDTO updatedAuthorDTO = new AuthorDTO(authorId, "Updated Author", null);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(existingAuthor)).thenReturn(existingAuthor);
        when(dtoConverter.convertToAuthorDTO(existingAuthor)).thenReturn(updatedAuthorDTO);

        // Act
        AuthorDTO updatedAuthor = authorService.updateAuthor(authorId, updatedAuthorDetails);

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
    void getAllAuthorBooks_shouldReturnBooks_whenAuthorExists() throws AuthorNotFoundException {
        // Arrange
        Long authorId = 1L;
        Author author = mockAuthors.get(0);
        author.setBooks(mockBooks);
        List<BookDTO> expectedBookDTOs = mockBooks.stream()
                .map(book -> new BookDTO(book.getId(), book.getTitle(), book.getISBN(), new ArrayList<>()))
                .toList();
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
        when(dtoConverter.convertToBookDTO(any(Book.class))).thenReturn(expectedBookDTOs.get(0));

        // Act
        List<BookDTO> authorBooks = authorService.getAllAuthorBooks(authorId);

        // Assert
        assertEquals(expectedBookDTOs.size(), authorBooks.size());
    }
}
