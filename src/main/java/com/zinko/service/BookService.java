package com.zinko.service;

import com.zinko.service.dto.bookDto.BookCreateDto;
import com.zinko.service.dto.bookDto.BookDto;

import java.util.List;

public interface BookService {

    BookDto findById(Long id);

    List<BookDto> findAll();

    BookDto create(BookCreateDto bookDto);

    BookDto update(BookDto bookDto);

    void delete(Long id);

}
