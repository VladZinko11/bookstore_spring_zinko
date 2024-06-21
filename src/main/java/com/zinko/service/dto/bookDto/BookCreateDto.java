package com.zinko.service.dto.bookDto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BookCreateDto {
    private String author;
    private String title;
    private String isbn;
    private LocalDate publicationDate;
    private BigDecimal price;
}
