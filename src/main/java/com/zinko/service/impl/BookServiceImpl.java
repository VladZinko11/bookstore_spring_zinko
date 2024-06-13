package com.zinko.service.impl;

import com.zinko.data.entity.Book;
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
        return serviceMapper.toDto(book);
    }

    @Override
    public List<BookDto> findAll() {
        log.debug("BookService method findAll call");
        List<Book> bookList = bookRepository.findAll();
        List<BookDto> bookDtoList = bookList.stream()
                .map(this.serviceMapper::toDto)
                .toList();
        if (bookDtoList.isEmpty()) {
            throw new EmptyRepositoryException("Books directory is empty");
        }
        return bookDtoList;
    }

    @Transactional
    @Override
    public void create(BookDto bookDto) {
        log.debug("BookService method create {}", bookDto);
        isbnValidate(bookDto);
        bookRepository.save(serviceMapper.toBook(bookDto));
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
    public void update(BookDto bookDto) {
        log.debug("BookService method update call {}", bookDto);
        isbnValidate(bookDto);
        bookRepository.save(serviceMapper.toBook(bookDto));
    }

    @Override
    public void delete(Long id) {
        log.debug("BookService method delete call with id: {}", id);
        if (!bookRepository.delete(id)) {
            throw new InvalidIndexException("Not found book with id: " + id);
        }
    }

    @Override
    public BookDto findByIsbn(String isbn) {
        log.debug("BookService method findByIsbn call with isbn: {}", isbn);
        return serviceMapper.toDto(bookRepository.findBookByIsbn(isbn)
                .orElseThrow(() -> new InvalidIndexException("Not found book with isbn: " + isbn)));
    }
}
