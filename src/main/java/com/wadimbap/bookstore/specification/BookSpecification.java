package com.wadimbap.bookstore.specification;

import com.wadimbap.bookstore.model.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
                title != null ? criteriaBuilder.like(root.get("title"), "%" + title + "%") : null;
    }

    public static Specification<Book> hasISBN(String isbn) {
        return (root, query, criteriaBuilder) ->
                isbn != null ? criteriaBuilder.like(root.get("isbn"), "%" + isbn + "%") : null;
    }

    public static Specification<Book> hasAuthorId(Long authorId) {
        return (root, query, criteriaBuilder) ->
                authorId != null ? criteriaBuilder.equal(root.join("authors").get("id"), authorId) : null;
    }
}
