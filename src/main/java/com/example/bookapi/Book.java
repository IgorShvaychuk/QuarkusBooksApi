package com.example.bookapi;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Year;

@Entity
public class Book extends PanacheEntity {
    @NotBlank(message = "Title is required")
    public String title;

    @NotBlank(message = "Author is required")
    public String author;

    @NotNull(message = "Publication year is required")
    public int publicationYear;

    @NotBlank(message = "Genre is required")
    public String genre;

    @NotBlank(message = "ISBN is required")
    @Size(min = 13, max = 13, message = "ISBN must contain exactly 13 characters")
    public String isbn;

    // Validation method to check publication year
    public boolean isPublicationYearValid() {
        return publicationYear <= Year.now().getValue();
    }
}
