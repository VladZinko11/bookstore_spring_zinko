package com.zinko.data.dao.impl;

import com.zinko.data.dao.connection.MyConnectionManager;
import com.zinko.data.dao.entity.Book;
import com.zinko.data.dao.BookDao;
import com.zinko.exception.MyRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class BookDaoImpl implements BookDao {

    private final MyConnectionManager myConnectionManager;
    public static final String SELECT_COUNT = "SELECT COUNT(*) FROM book WHERE deleted=false";
    public static final String SELECT_ALL_BY_AUTHOR = "SELECT id, author, title, isbn, publication_date FROM book WHERE author=? AND deleted=false";
    public static final String DELETE = "UPDATE book SET deleted=true WHERE id=?";
    public static final String SELECT_BY_ID = "SELECT id, author, title, isbn, publication_date FROM book WHERE id=? AND deleted=false";

    public static final String INSERT = "INSERT INTO book (author, title, isbn, publication_date, deleted) VALUES (?, ?, ?, ?, false)";
    public static final String SELECT_ALL = "SELECT id, author, title, isbn, publication_date FROM book WHERE deleted=false";
    public static final String SELECT_BY_ISBN = "SELECT id, author, title, isbn, publication_date FROM book WHERE isbn=? AND deleted=false";
    public static final String UPDATE = "UPDATE book SET author=?, title=?,publication_date=? WHERE isbn=? AND deleted=false";
    public static final int PARAMETER_INDEX_1 = 1;
    public static final int PARAMETER_INDEX_2 = 2;
    public static final int PARAMETER_INDEX_3 = 3;
    public static final int PARAMETER_INDEX_4 = 4;
    public static final int COLUMN_INDEX_1 = 1;
    public static final int COLUMN_INDEX_2 = 2;
    public static final int COLUMN_INDEX_3 = 3;
    public static final int COLUMN_INDEX_4 = 4;
    public static final int COLUMN_INDEX_5 = 5;

    private static Book creatAndInitBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong(COLUMN_INDEX_1));
        book.setAuthor(resultSet.getString(COLUMN_INDEX_2));
        book.setTitle(resultSet.getString(COLUMN_INDEX_3));
        book.setIsbn(resultSet.getString(COLUMN_INDEX_4));
        if (resultSet.getDate(COLUMN_INDEX_5) != null) {
            book.setPublicationDate(resultSet.getDate(COLUMN_INDEX_5).toLocalDate());
        } else book.setPublicationDate(null);
        return book;
    }

    @Override
    public Book creatBook(Book book) {
        try (Connection connection = myConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {
            if (findBookByIsbn(book.getIsbn()) == null) {
                statement.setString(PARAMETER_INDEX_1, book.getAuthor());
                statement.setString(PARAMETER_INDEX_2, book.getTitle());
                statement.setString(PARAMETER_INDEX_3, book.getIsbn());
                statement.setDate(PARAMETER_INDEX_4, Date.valueOf(book.getPublicationDate()));
                statement.executeUpdate();
                return findBookByIsbn(book.getIsbn());
            } else return null;
        } catch (SQLException e) {
            throw new MyRuntimeException("Oops, something wrong on server", e, 500);
        }
    }

    @Override
    public Book findBookById(Long id) {
        try (Connection connection = myConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(PARAMETER_INDEX_1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return creatAndInitBookFromResultSet(resultSet);
            else return null;
        } catch (SQLException e) {
            throw new MyRuntimeException("Oops, something wrong on server", e, 500);
        }
    }

    @Override
    public List<Book> findAllBook() {
        List<Book> list = new ArrayList<>();
        try (Connection connection = myConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                list.add(creatAndInitBookFromResultSet(resultSet));
            }
            return list;
        } catch (SQLException e) {
            throw new MyRuntimeException("Oops, something wrong on server", e, 500);
        }
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        try (Connection connection = myConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ISBN)) {
            statement.setString(PARAMETER_INDEX_1, isbn);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return creatAndInitBookFromResultSet(resultSet);
            else return null;
        } catch (SQLException e) {
            throw new MyRuntimeException("Oops, something wrong on server", e, 500);
        }
    }

    @Override
    public Book updateBook(Book book) {
        log.debug("BookDao method updateBook call {}", book);
        try (Connection connection = myConnectionManager.getConnection()) {
            Book book1 = findBookByIsbn(book.getIsbn());
            if (book1 != null) {
                try (PreparedStatement statement1 = connection.prepareStatement(UPDATE)) {
                    statement1.setString(PARAMETER_INDEX_1, book.getAuthor());
                    statement1.setString(PARAMETER_INDEX_2, book.getTitle());
                    statement1.setDate(PARAMETER_INDEX_3, Date.valueOf(book.getPublicationDate()));
                    statement1.setString(PARAMETER_INDEX_4, book.getIsbn());
                    statement1.executeUpdate();
                    return findBookByIsbn(book.getIsbn());
                }
            } else return null;
        } catch (SQLException e) {
            throw new MyRuntimeException("Oops, something wrong on server", e, 500);
        }
    }

    @Override
    public boolean deleteBook(Long id) {
        try (Connection connection = myConnectionManager.getConnection()) {
            Book book = findBookById(id);
            if (book != null) {
                try (PreparedStatement statement1 = connection.prepareStatement(DELETE)) {
                    statement1.setLong(PARAMETER_INDEX_1, id);
                    statement1.executeUpdate();
                    return true;
                }
            } else return false;
        } catch (SQLException e) {
            throw new MyRuntimeException("Oops, something wrong on server", e, 500);
        }
    }

    @Override
    public List<Book> findByAuthor(String author) {
        List<Book> list = new ArrayList<>();
        try (Connection connection = myConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_AUTHOR)) {
            statement.setString(PARAMETER_INDEX_1, author);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(creatAndInitBookFromResultSet(resultSet));
            }
            return list;
        } catch (SQLException e) {
            throw new MyRuntimeException("Oops, something wrong on server", e, 500);
        }
    }

    @Override
    public Long countAll() {
        try (Connection connection = myConnectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_COUNT);
            return resultSet.getLong(COLUMN_INDEX_1);
        } catch (SQLException e) {
            throw new MyRuntimeException("Oops, something wrong on server", e, 500);
        }
    }
}
