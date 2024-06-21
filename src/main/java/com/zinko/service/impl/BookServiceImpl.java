package com.zinko.service.impl;

import com.zinko.data.entity.Book;
import com.zinko.data.repository.BookRepository;
import com.zinko.service.BookService;
import com.zinko.service.dto.bookDto.BookCreateDto;
import com.zinko.service.dto.bookDto.BookDto;
import com.zinko.service.exception.EmptyRepositoryException;
import com.zinko.service.exception.InvalidIndexException;
import com.zinko.service.exception.OccupiedElementException;
import com.zinko.service.serviceMapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new InvalidIndexException("Not found book with id: " + id));
        return serviceMapper.toBookDtoFromBook(book);
    }

    @Override
    public List<BookDto> findAll() {
        log.debug("BookService method findAll call");
        List<Book> bookList = bookRepository.findAll();
        List<BookDto> bookDtoList = bookList.stream()
                .map(this.serviceMapper::toBookDtoFromBook)
                .toList();
        if (bookDtoList.isEmpty()) {
            throw new EmptyRepositoryException("Books directory is empty");
        }
        return bookDtoList;
    }

    @Transactional
    @Override
    public BookDto create(BookCreateDto bookCreateDto) {
        log.debug("BookService method create {}", bookCreateDto);
        BookDto bookDto = serviceMapper.toBookDtoFromBookCreateDto(bookCreateDto);
        isbnValidate(bookDto);
        Book book = bookRepository.save(serviceMapper.toBookFromBookDto(bookDto));
        return serviceMapper.toBookDtoFromBook(book);
    }

    private void isbnValidate(BookDto bookDto) {
        String isbn = bookDto.getIsbn();
        Optional<Book> optionalBook = bookRepository.findBookByIsbn(isbn);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (!Objects.equals(book.getId(), bookDto.getId())) {
                throw new OccupiedElementException("Book with isbn: " + bookDto.getIsbn() + " is exist");
            }
        }
    }

    @Transactional
    @Override
    public BookDto update(BookDto bookDto) {
        log.debug("BookService method update call {}", bookDto);
        isbnValidate(bookDto);
        bookRepository.save(serviceMapper.toBookFromBookDto(bookDto));
        return bookDto;
    }

    @Override
    public void delete(Long id) {
        log.debug("BookService method delete call with id: {}", id);
        if (!bookRepository.delete(id)) {
            throw new InvalidIndexException("Not found book with id: " + id);
        }
    }

}
