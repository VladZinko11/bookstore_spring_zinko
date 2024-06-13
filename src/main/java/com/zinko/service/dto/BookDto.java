package com.zinko.service.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class BookDto {

    private Long id;
    private String author;
    private String title;
    private String isbn;
    private LocalDate publicationDate;
    private BigDecimal price;
}
