package com.zinko.data.repository.impl;

import com.zinko.data.entity.Book;
import com.zinko.data.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public class BookRepositoryImpl implements BookRepository {
    public static final String SELECT_ALL = "FROM Book WHERE deleted=false";
    private static final String SELECT_BY_ISBN = "FROM Book WHERE isbn=:isbn AND deleted=false";
    @PersistenceContext
    private EntityManager manager;

    @Override
    public void save(Book entity) {
        if (entity.getId() != null) {
            manager.merge(entity);
        } else {
            manager.persist(entity);
        }
    }

    @Override
    public Optional<Book> findById(Long key) {
        Book book = manager.find(Book.class, key);
        if (book != null && book.getDeleted()) {
            return Optional.empty();
        }
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        return manager.createQuery(SELECT_ALL, Book.class).getResultList();
    }

    @Override
    public Optional<Book> findBookByIsbn(String isbn) {
        try {
            Optional<Book> optionalBook = Optional.ofNullable(manager.createQuery(SELECT_BY_ISBN, Book.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult());
            return optionalBook;
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(Long id) {
        Book book = manager.find(Book.class, id);
        if (book == null) {
            return false;
        }
        book.setDeleted(true);
        return true;
    }
}
