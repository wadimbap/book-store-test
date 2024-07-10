package com.wadimbap.bookstore.converter;

import com.wadimbap.bookstore.dto.AuthorDTO;
import com.wadimbap.bookstore.dto.BookDTO;
import com.wadimbap.bookstore.model.Author;
import com.wadimbap.bookstore.model.Book;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DTOConverter {

    public AuthorDTO convertToAuthorDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());
        authorDTO.setBookIds(author.getBooks().stream().map(Book::getId).collect(Collectors.toList()));
        return authorDTO;
    }

    public BookDTO convertToBookDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setISBN(book.getISBN());
        bookDTO.setAuthorIds(book.getAuthors().stream().map(Author::getId).collect(Collectors.toList()));
        return bookDTO;
    }
}
