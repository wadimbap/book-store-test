package com.wadimbap.bookstore.service.implementation;

import com.wadimbap.bookstore.converter.DTOConverter;
import com.wadimbap.bookstore.dto.AuthorDTO;
import com.wadimbap.bookstore.dto.BookDTO;
import com.wadimbap.bookstore.exception.AuthorNotFoundException;
import com.wadimbap.bookstore.model.Author;
import com.wadimbap.bookstore.repository.AuthorRepository;
import com.wadimbap.bookstore.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final DTOConverter dtoConverter;

    @Override
    public AuthorDTO saveAuthor(Author author) {
        Author savedAuthor = authorRepository.save(author);
        return dtoConverter.convertToAuthorDTO(savedAuthor);
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository
                .findAll()
                .stream()
                .map(dtoConverter::convertToAuthorDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getAllAuthorBooks(Long authorId) throws AuthorNotFoundException {
        Author author = findAuthorOrThrowException(authorId);
        return author
                .getBooks()
                .stream()
                .map(dtoConverter::convertToBookDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorDTO> getAuthorById(Long authorId) {
        return authorRepository
                .findById(authorId)
                .map(dtoConverter::convertToAuthorDTO);
    }

    @Override
    public AuthorDTO updateAuthor(Long authorId, Author authorDetails) throws AuthorNotFoundException {
        Author author = findAuthorOrThrowException(authorId);
        author.setName(authorDetails.getName());
        Author updatedAuthor = authorRepository.save(author);
        return dtoConverter.convertToAuthorDTO(updatedAuthor);
    }

    @Override
    public void deleteAuthor(Long authorId) throws AuthorNotFoundException {
        Author author = findAuthorOrThrowException(authorId);
        authorRepository.delete(author);
    }

    private Author findAuthorOrThrowException(Long authorId) throws AuthorNotFoundException {
        return authorRepository.findById(authorId).orElseThrow(
                () -> new AuthorNotFoundException("Author not found")
        );
    }
}
