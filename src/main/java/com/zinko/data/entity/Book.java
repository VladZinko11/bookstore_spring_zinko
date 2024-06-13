package com.zinko.data.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "publication_date")
    private LocalDate publicationDate = LocalDate.now();

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "price")
    private BigDecimal price;

}
