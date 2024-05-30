package com.zinko.service.impl;

import com.zinko.data.dao.entity.Book;
import com.zinko.data.repository.BookRepository;
import com.zinko.exception.EmptyRepositoryException;
import com.zinko.exception.InvalidIndexException;
import com.zinko.exception.OccupiedElementException;
import com.zinko.service.BookService;
import com.zinko.service.dto.BookDto;
import com.zinko.service.serviceMapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public BookDto findById(Long id) {
        log.debug("BookService method findById call with id: {}", id);
        Book book = bookRepository.findBookById(id).orElseThrow(() -> new InvalidIndexException("Not found book with id: " + id));
        BookDto bookDto = serviceMapper.toDto(book);
        return bookDto;
    }

    @Override
    public List<BookDto> findAll() {
        log.debug("BookService method findAll call");
        List<Book> bookList = bookRepository.findAllBook().stream().filter(Optional::isPresent).map(Optional::get).toList();
        List<BookDto> bookDtoList = bookList.stream().map(this.serviceMapper::toDto).toList();
        if (bookDtoList.isEmpty()) throw new EmptyRepositoryException("Books directory is empty");
        else return bookDtoList;
    }

    @Override
    public BookDto create(BookDto bookDto) {
        log.debug("BookService method create {}", bookDto);
        if (bookRepository.findBookByIsbn(bookDto.getIsbn()).isEmpty()) {
            Book book = bookRepository.creatBook(serviceMapper.toBook(bookDto)).orElseThrow(RuntimeException::new);
            return serviceMapper.toDto(book);
        } else throw new OccupiedElementException("Book with isbn: " + bookDto.getIsbn() + " is exist");
    }

    @Override
    public BookDto update(BookDto bookDto) {
        log.debug("BookService method update call {}", bookDto);
        Optional<Book> optionalBook = bookRepository.findBookByIsbn(bookDto.getIsbn());
        if (optionalBook.isPresent()) {
            if (!Objects.equals(optionalBook.get().getId(), bookDto.getId())) {
                throw new OccupiedElementException("Book with isbn " + bookDto.getIsbn() + " is exist");
            }
        }
        Book newBook = bookRepository.updateBook(serviceMapper.toBook(bookDto)).orElseThrow(RuntimeException::new);
        return serviceMapper.toDto(newBook);
    }

    @Override
    public void delete(Long id) {
        log.debug("BookService method delete call with id: {}", id);
        if (!bookRepository.deleteBook(id)) throw new InvalidIndexException("Not found book with id: " + id);
    }
}
