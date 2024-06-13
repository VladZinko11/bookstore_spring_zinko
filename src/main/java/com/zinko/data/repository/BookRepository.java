package com.zinko.data.repository;

import com.zinko.data.entity.Book;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findBookByIsbn(String isbn);

}
