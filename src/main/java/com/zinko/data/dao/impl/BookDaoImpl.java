package com.zinko.data.dao.impl;

import com.zinko.data.dao.entity.Book;
import com.zinko.data.dao.BookDao;
import com.zinko.exception.EmptyRepositoryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BookDaoImpl implements BookDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public static final String SELECT_COUNT = "SELECT COUNT(*) FROM book WHERE deleted=false";
    public static final String SELECT_ALL_BY_AUTHOR = "SELECT id, author, title, isbn, publication_date FROM book WHERE author=? AND deleted=false";
    public static final String DELETE = "UPDATE book SET deleted=true WHERE id=?";
    public static final String SELECT_BY_ID = "SELECT id, author, title, isbn, publication_date FROM book WHERE id=? AND deleted=false";

    public static final String INSERT = "INSERT INTO book (author, title, isbn, publication_date, deleted) VALUES (?, ?, ?, ?, false)";
    public static final String SELECT_ALL = "SELECT id, author, title, isbn, publication_date FROM book WHERE deleted=false";
    public static final String SELECT_BY_ISBN = "SELECT id, author, title, isbn, publication_date FROM book WHERE isbn=? AND deleted=false";
    public static final String UPDATE = "UPDATE book SET author=:author, title=:title,publication_date=:publication_date, isbn=:isbn WHERE id=:id AND deleted=false";
    public static final int PARAMETER_INDEX_1 = 1;
    public static final int PARAMETER_INDEX_2 = 2;
    public static final int PARAMETER_INDEX_3 = 3;
    public static final int PARAMETER_INDEX_4 = 4;
    public static final int COLUMN_INDEX_1 = 1;
    public static final int COLUMN_INDEX_2 = 2;
    public static final int COLUMN_INDEX_3 = 3;
    public static final int COLUMN_INDEX_4 = 4;
    public static final int COLUMN_INDEX_5 = 5;

    private Book mapRow(ResultSet rs, int num) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong(COLUMN_INDEX_1));
        book.setAuthor(rs.getString(COLUMN_INDEX_2));
        book.setTitle(rs.getString(COLUMN_INDEX_3));
        book.setIsbn(rs.getString(COLUMN_INDEX_4));
        book.setPublicationDate(rs.getDate(COLUMN_INDEX_5).toLocalDate());
        return book;
    }

    @Override
    public Book creatBook(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int update = jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(PARAMETER_INDEX_1, book.getAuthor());
            statement.setString(PARAMETER_INDEX_2, book.getTitle());
            statement.setString(PARAMETER_INDEX_3, book.getIsbn());
            statement.setDate(PARAMETER_INDEX_4, Date.valueOf(book.getPublicationDate()));
            return statement;
        }, keyHolder);
        if (update == 1) {
            Book newBook = findBookById(keyHolder.getKey().longValue());
            return newBook;
        } else return null;
    }

    @Override
    public Book findBookById(Long id) {
        try {
            Book book = jdbcTemplate.queryForObject(SELECT_BY_ID, this::mapRow, id);
            return book;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Book> findAllBook() {
        List<Book> list = jdbcTemplate.query(SELECT_ALL, this::mapRow);
        return list;
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        try {
            Book book = jdbcTemplate.queryForObject(SELECT_BY_ISBN, this::mapRow, isbn);
            return book;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public Book updateBook(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("author", book.getAuthor());
        params.put("title", book.getTitle());
        params.put("publication_date", Date.valueOf(book.getPublicationDate()));
        params.put("isbn", book.getIsbn());
        params.put("id", book.getId());
        int update = namedParameterJdbcTemplate.update(UPDATE, params);
        if (update == 1) {
            Book updatedBook = findBookById(book.getId());
            return updatedBook;
        } else return null;
    }

    @Override
    public boolean deleteBook(Long id) {
        int update = jdbcTemplate.update(DELETE, id);
        return update == 1;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        List<Book> list = jdbcTemplate.query(SELECT_ALL_BY_AUTHOR, this::mapRow, author);
        return list;
    }

    @Override
    public Long countAll() {
        try {
            Long count = jdbcTemplate.queryForObject(SELECT_COUNT, (rs, rowNum) -> rs.getLong(COLUMN_INDEX_1));
            return count;
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new EmptyRepositoryException("Not found books in repository");
        }
    }
}
