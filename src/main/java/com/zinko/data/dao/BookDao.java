package com.zinko.data.dao;

import com.zinko.data.dao.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    Optional<Book> creatBook(Book book);

    Optional<Book> findBookById(Long id);

    List<Optional<Book>> findAllBook();

    Optional<Book> findBookByIsbn(String isbn);

    Optional<Book> updateBook(Book book);

    boolean deleteBook(Long id);

    List<Optional<Book>> findByAuthor(String author);

    Long countAll();

}
