package com.zinko.data.repository.impl;

import com.zinko.data.dao.BookDao;
import com.zinko.data.dao.entity.Book;
import com.zinko.data.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final BookDao bookDao;

    @Override
    public Optional<Book> creatBook(Book book) {
        return bookDao.creatBook(book);
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        return bookDao.findBookById(id);
    }

    @Override
    public List<Optional<Book>> findAllBook() {
        return bookDao.findAllBook();
    }

    @Override
    public Optional<Book> findBookByIsbn(String isbn) {
        return bookDao.findBookByIsbn(isbn);
    }

    @Override
    public Optional<Book> updateBook(Book book) {
        return bookDao.updateBook(book);
    }

    @Override
    public boolean deleteBook(Long id) {
        return bookDao.deleteBook(id);
    }

    @Override
    public List<Optional<Book>> findByAuthor(String author) {
        return bookDao.findByAuthor(author);
    }
}
