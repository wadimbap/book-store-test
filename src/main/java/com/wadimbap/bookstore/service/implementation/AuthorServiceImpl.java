package com.wadimbap.bookstore.service.implementation;

import com.wadimbap.bookstore.exception.AuthorNotFoundException;
import com.wadimbap.bookstore.model.Author;
import com.wadimbap.bookstore.model.Book;
import com.wadimbap.bookstore.repository.AuthorRepository;
import com.wadimbap.bookstore.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public List<Book> getAllAuthorBooks(Long authorId) {
        return authorRepository.getAuthorBooks(authorId);
    }

    @Override
    public Optional<Author> getAuthorById(Long authorId) {
        return authorRepository.findById(authorId);
    }

    @Override
    public Author updateAuthor(Long authorId, Author authorDetails) throws AuthorNotFoundException {
        Author author = findAuthorOrThrowException(authorId);
        author.setName(authorDetails.getName());
        return authorRepository.save(author);
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
