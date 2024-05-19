package com.zinko.service.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class BookDto {

    private Long id;
    private String author;
    private String title;
    private String isbn;
    private LocalDate publicationDate;

}
