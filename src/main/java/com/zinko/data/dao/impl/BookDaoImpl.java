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
import java.sql.Date;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BookDaoImpl implements BookDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public static final String SELECT_COUNT = "SELECT COUNT(*) FROM book WHERE deleted=false";
    public static final String SELECT_ALL_BY_AUTHOR = "SELECT id, author, title, isbn, publication_date, price FROM book WHERE author=? AND deleted=false";
    public static final String DELETE = "UPDATE book SET deleted=true WHERE id=? AND deleted=false";
    public static final String SELECT_BY_ID = "SELECT id, author, title, isbn, publication_date, price FROM book WHERE id=? AND deleted=false";

    public static final String INSERT = "INSERT INTO book (author, title, isbn, publication_date, price, deleted) VALUES (?, ?, ?, ?, ?, false)";
    public static final String SELECT_ALL = "SELECT id, author, title, isbn, publication_date, price FROM book WHERE deleted=false";
    public static final String SELECT_BY_ISBN = "SELECT id, author, title, isbn, publication_date, price FROM book WHERE isbn=? AND deleted=false";
    public static final String UPDATE = "UPDATE book SET author=:author, title=:title,publication_date=:publication_date, isbn=:isbn WHERE id=:id AND deleted=false";
    public static final int PARAMETER_INDEX_1 = 1;
    public static final int PARAMETER_INDEX_2 = 2;
    public static final int PARAMETER_INDEX_3 = 3;
    public static final int PARAMETER_INDEX_4 = 4;
    private static final int PARAMETER_INDEX_5 = 5;
    public static final int COLUMN_INDEX_1 = 1;
    public static final int COLUMN_INDEX_2 = 2;
    public static final int COLUMN_INDEX_3 = 3;
    public static final int COLUMN_INDEX_4 = 4;
    public static final int COLUMN_INDEX_5 = 5;
    private static final int COLUMN_INDEX_6 = 6;

    private Optional<Book> mapRow(ResultSet rs, int num) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong(COLUMN_INDEX_1));
        book.setAuthor(rs.getString(COLUMN_INDEX_2));
        book.setTitle(rs.getString(COLUMN_INDEX_3));
        book.setIsbn(rs.getString(COLUMN_INDEX_4));
        book.setPublicationDate(rs.getDate(COLUMN_INDEX_5).toLocalDate());
        book.setPrice(rs.getBigDecimal(COLUMN_INDEX_6));
        return Optional.ofNullable(book);
    }

    @Override
    public Optional<Book> creatBook(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int update = jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(PARAMETER_INDEX_1, book.getAuthor());
            statement.setString(PARAMETER_INDEX_2, book.getTitle());
            statement.setString(PARAMETER_INDEX_3, book.getIsbn());
            statement.setDate(PARAMETER_INDEX_4, Date.valueOf(book.getPublicationDate()));
            statement.setBigDecimal(PARAMETER_INDEX_5, book.getPrice());
            return statement;
        }, keyHolder);
        Optional<Book> optionalBook = findBookById((Long) Objects.requireNonNull(keyHolder.getKeys()).get("id"));
        return optionalBook;
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        try {
            Optional<Book> optionalBook = jdbcTemplate.queryForObject(SELECT_BY_ID, this::mapRow, id);
            return optionalBook;
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Optional<Book>> findAllBook() {
        List<Optional<Book>> list = jdbcTemplate.query(SELECT_ALL, this::mapRow);
        return list;
    }

    @Override
    public Optional<Book> findBookByIsbn(String isbn) {
        try {
            Optional<Book> optionalBook = jdbcTemplate.queryForObject(SELECT_BY_ISBN, this::mapRow, isbn);
            return optionalBook;
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> updateBook(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("author", book.getAuthor());
        params.put("title", book.getTitle());
        params.put("publication_date", Date.valueOf(book.getPublicationDate()));
        params.put("isbn", book.getIsbn());
        params.put("id", book.getId());
        int update = namedParameterJdbcTemplate.update(UPDATE, params);
        Optional<Book> optionalBook = findBookById(book.getId());
        return optionalBook;
    }

    @Override
    public boolean deleteBook(Long id) {
        int update = jdbcTemplate.update(DELETE, id);
        return update == 1;
    }

    @Override
    public List<Optional<Book>> findByAuthor(String author) {
        List<Optional<Book>> list = jdbcTemplate.query(SELECT_ALL_BY_AUTHOR, this::mapRow, author);
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
