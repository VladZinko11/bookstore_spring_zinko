package com.zinko.data.dao.entity;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Book {

    private Long id;
    private String author;
    private String title;
    private String isbn;
    private LocalDate publicationDate = LocalDate.now();
    private BigDecimal price;

}
