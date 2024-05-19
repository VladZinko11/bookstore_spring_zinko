package com.zinko.data.dao.impl;

import com.zinko.data.dao.UserDao;
import com.zinko.data.dao.connection.MyConnectionManager;
import com.zinko.data.dao.entity.User;
import com.zinko.data.dao.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class UserDaoImpl implements UserDao {
    private final MyConnectionManager connectionManager;
    private static final int PARAMETER_INDEX_1 = 1;
    private static final int PARAMETER_INDEX_2 = 2;
    private static final int PARAMETER_INDEX_3 = 3;
    private static final int PARAMETER_INDEX_4 = 4;
    private static final int PARAMETER_INDEX_5 = 5;
    private static final int COLUMN_INDEX_1 = 1;
    private static final int COLUMN_INDEX_2 = 2;
    private static final int COLUMN_INDEX_3 = 3;
    private static final int COLUMN_INDEX_4 = 4;
    private static final int COLUMN_INDEX_5 = 5;
    private static final int COLUMN_INDEX_6 = 6;
    private static final int PARAMETER_INDEX_6 = 6;
    private static final String SELECT_COUNT = "SELECT COUNT(*) FROM public.user WHERE deleted=false";
    private static final String SELECT_BY_LAST_NAME = "SELECT u.id, u.first_name, u.last_name, u.email, u.password, e.role FROM public.user AS u JOIN public.enum_role AS e ON u.id_enum_role=e.id WHERE u.last_name=? AND u.deleted=false";
    private static final String SELECT_BY_EMAIL = "SELECT u.id, u.first_name, u.last_name, u.email, u.password, e.role FROM public.user AS u JOIN public.enum_role AS e ON u.id_enum_role=e.id WHERE u.email=? AND u.deleted=false";
    private static final String DELETE = "UPDATE public.user SET deleted=true WHERE id=?";
    private static final String UPDATE = "UPDATE public.user SET first_name=?, last_name=?, email=?, password=?, id_enum_role=? WHERE id=? AND deleted=false";
    private static final String SELECT_ALL = "SELECT u.id, u.first_name, u.last_name, u.email, u.password, e.role FROM public.user AS u JOIN public.enum_role AS e ON u.id_enum_role=e.id WHERE u.deleted=false ORDER BY u.id";
    private static final String SELECT_BY_ID = "SELECT u.id, u.first_name, u.last_name, u.email, u.password, e.role FROM public.user AS u JOIN public.enum_role AS e ON u.id_enum_role=e.id WHERE u.id=? AND u.deleted=false";
    private static final String CREATE = "INSERT INTO public.user (first_name, last_name, email, password, id_enum_role, deleted) VALUES (?, ?, ?, ?, ?, false)";
    private static final String SELECT_ID_ROLE = "SELECT id FROM enum_role WHERE role=?";

    private static User getUserFromResulSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(COLUMN_INDEX_1));
        user.setFirstName(resultSet.getString(COLUMN_INDEX_2));
        user.setLastName(resultSet.getString(COLUMN_INDEX_3));
        user.setEmail(resultSet.getString(COLUMN_INDEX_4));
        user.setPassword(resultSet.getString(COLUMN_INDEX_5));
        user.setRole(Role.valueOf(resultSet.getString(COLUMN_INDEX_6)));
        return user;
    }

    private static Long getIdRole(User user, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_ID_ROLE);
        statement.setString(PARAMETER_INDEX_1, user.getRole().toString());
        ResultSet resultSet = statement.executeQuery();
        log.debug("a database access occurred");
        resultSet.next();
        return resultSet.getLong(COLUMN_INDEX_1);
    }

    @Override
    public User create(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE)) {
            log.debug("a database access occurred");
            if (findByEmail(user.getEmail()) == null) {
                statement.setString(PARAMETER_INDEX_1, user.getFirstName());
                statement.setString(PARAMETER_INDEX_2, user.getLastName());
                statement.setString(PARAMETER_INDEX_3, user.getEmail());
                statement.setString(PARAMETER_INDEX_4, user.getPassword());
                if(user.getRole()==null) user.setRole(Role.CUSTOMER);
                statement.setLong(PARAMETER_INDEX_5, getIdRole(user, connection));
                statement.executeUpdate();
                return findByEmail(user.getEmail());
            } else return null;
        } catch (SQLException e) {
            throw new RuntimeException("Oops, something wrong on server", e);
        }
    }

    @Override
    public User findById(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);) {
            statement.setLong(PARAMETER_INDEX_1, id);
            ResultSet resultSet = statement.executeQuery();
            log.debug("a database access occurred");
            if (resultSet.next()) return getUserFromResulSet(resultSet);
            else return null;
        } catch (SQLException e) {
            throw new RuntimeException("Oops, something wrong on server", e);
        }
    }


    @Override
    public List<User> findAll() {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            log.debug("a database access occurred");
            List<User> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(getUserFromResulSet(resultSet));
            }
            if (list.isEmpty()) return null;
            else return list;
        } catch (SQLException e) {
            throw new RuntimeException("Oops, something wrong on server", e);
        }
    }

    @Override
    public User update(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            log.debug("a database access occurred");
            if (findById(user.getId()) != null) {
                statement.setString(PARAMETER_INDEX_1, user.getFirstName());
                statement.setString(PARAMETER_INDEX_2, user.getLastName());
                statement.setString(PARAMETER_INDEX_3, user.getEmail());
                statement.setString(PARAMETER_INDEX_4, user.getPassword());
                statement.setLong(PARAMETER_INDEX_5, getIdRole(user, connection));
                statement.setLong(PARAMETER_INDEX_6, user.getId());
                statement.executeUpdate();
                log.debug("a database access occurred");
                return findById(user.getId());
            } else return null;
        } catch (SQLException e) {
            throw new RuntimeException("Oops, something wrong on server", e);
        }
    }

    @Override
    public boolean delete(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            log.debug("a database access occurred");
            if (findById(user.getId()) != null) {
                statement.setLong(PARAMETER_INDEX_1, user.getId());
                statement.executeUpdate();
                return true;
            } else return false;
        } catch (SQLException e) {
            throw new RuntimeException("Oops, something wrong on server", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_EMAIL)) {
            statement.setString(PARAMETER_INDEX_1, email);
            ResultSet resultSet = statement.executeQuery();
            log.debug("a database access occurred");
            if (resultSet.next()) return getUserFromResulSet(resultSet);
            else return null;
        } catch (SQLException e) {
            throw new RuntimeException("Oops, something wrong on server", e);
        }
    }

    @Override
    public List<User> findByLastName(String lastName) {
        List<User> list = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_LAST_NAME);) {
            statement.setString(PARAMETER_INDEX_1, lastName);
            ResultSet resultSet = statement.executeQuery();
            log.debug("a database access occurred");
            while (resultSet.next()) {
                list.add(getUserFromResulSet(resultSet));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Oops, something wrong on server", e);
        }
    }

    @Override
    public Long countAll() {
        try (Connection connection = connectionManager.getConnection();
             Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(SELECT_COUNT);
            log.debug("a database access occurred");
            return resultSet.getLong(COLUMN_INDEX_1);
        } catch (SQLException e) {
            throw new RuntimeException("Oops, something wrong on server", e);
        }
    }
}
