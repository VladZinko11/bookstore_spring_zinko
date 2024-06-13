package com.zinko.service;

import com.zinko.service.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto findById(Long id);

    List<BookDto> findAll();

    void create(BookDto bookDto);

    void update(BookDto bookDto);

    void delete(Long id);

    BookDto findByIsbn(String isbn);
}
