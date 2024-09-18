package com.example.bookapi;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookRepository implements PanacheRepository<Book> {

    public Book findByIsbn(String isbn) {
        return find("isbn", isbn).firstResult();
    }
}
