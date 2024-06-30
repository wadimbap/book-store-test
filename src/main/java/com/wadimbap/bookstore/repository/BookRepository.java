package com.wadimbap.bookstore.repository;

import com.wadimbap.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingAndISBNContainingAndAuthorsId(String title, String isbn, Long authorId);

    List<Book> findByTitleContainingAndISBNContaining(String title, String isbn);

    List<Book> findByTitleContaining(String title);

    List<Book> findByISBNContaining(String isbn);

    List<Book> findByAuthorsId(Long authorId);
}
