package com.example.bookapi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class BookService {
    @Inject
    BookRepository repository;

    public List<Book> listAllBooks() {
        return repository.listAll();
    }

    public Book getBookById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Book addBook(Book book) {
        // Checking the year of publication
        if (!book.isPublicationYearValid()) {
            throw new IllegalArgumentException("Publication year cannot be later than the current year");
        }

        // Checking for ISBN uniqueness
        if (repository.findByIsbn(book.isbn) != null) {
            throw new IllegalArgumentException("ISBN must be unique");
        }

        repository.persist(book);
        return book;
    }

    @Transactional
    public Book updateBook(Long id, Book updatedBook) {
        Book book = repository.findById(id);
        if (book != null) {
            // Checking the year of publication
            if (!updatedBook.isPublicationYearValid()) {
                throw new IllegalArgumentException("Publication year cannot be later than the current year");
            }

            // Checking for ISBN uniqueness
            if (!book.isbn.equals(updatedBook.isbn) && repository.findByIsbn(updatedBook.isbn) != null) {
                throw new IllegalArgumentException("ISBN must be unique");
            }

            book.title = updatedBook.title;
            book.author = updatedBook.author;
            book.publicationYear = updatedBook.publicationYear;
            book.genre = updatedBook.genre;
            book.isbn = updatedBook.isbn;
        }
        return book;
    }

    @Transactional
    public void deleteBook(Long id) {
        repository.deleteById(id);
    }
}
