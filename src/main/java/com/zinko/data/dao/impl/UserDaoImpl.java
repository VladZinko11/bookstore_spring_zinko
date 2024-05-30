package com.zinko.data.dao.impl;

import com.zinko.data.dao.UserDao;
import com.zinko.data.dao.entity.User;
import com.zinko.data.dao.entity.enums.Role;
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
import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
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
    private static final String SELECT_COUNT = "SELECT COUNT(*) FROM public.user WHERE deleted=false";
    private static final String SELECT_BY_LAST_NAME = "SELECT u.id, u.first_name, u.last_name, u.email, u.password, e.role FROM public.user AS u JOIN public.enum_role AS e ON u.id_enum_role=e.id WHERE u.last_name=? AND u.deleted=false";
    private static final String SELECT_BY_EMAIL = "SELECT u.id, u.first_name, u.last_name, u.email, u.password, e.role FROM public.user AS u JOIN public.enum_role AS e ON u.id_enum_role=e.id WHERE u.email=? AND u.deleted=false";
    private static final String DELETE = "UPDATE public.user SET deleted=true WHERE id=?";
    private static final String UPDATE = "UPDATE public.user SET first_name=:first_name, last_name=:last_name, email=:email, password=:password, id_enum_role=(SELECT id FROM enum_role WHERE role=:role) WHERE id=:id AND deleted=false";
    private static final String SELECT_ALL = "SELECT u.id, u.first_name, u.last_name, u.email, u.password, e.role FROM public.user AS u JOIN public.enum_role AS e ON u.id_enum_role=e.id WHERE u.deleted=false ORDER BY u.id";
    private static final String SELECT_BY_ID = "SELECT u.id, u.first_name, u.last_name, u.email, u.password, e.role FROM public.user AS u JOIN public.enum_role AS e ON u.id_enum_role=e.id WHERE u.id=? AND u.deleted=false";
    private static final String CREATE = "INSERT INTO public.user (first_name, last_name, email, password, id_enum_role, deleted) VALUES (?, ?, ?, ?, (SELECT id FROM enum_role WHERE role=?), false)";

    private Optional<User> mapRow(ResultSet resultSet, int num) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(COLUMN_INDEX_1));
        user.setFirstName(resultSet.getString(COLUMN_INDEX_2));
        user.setLastName(resultSet.getString(COLUMN_INDEX_3));
        user.setEmail(resultSet.getString(COLUMN_INDEX_4));
        user.setPassword(resultSet.getString(COLUMN_INDEX_5));
        user.setRole(Role.valueOf(resultSet.getString(COLUMN_INDEX_6)));
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int update = jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(PARAMETER_INDEX_1, user.getFirstName());
            statement.setString(PARAMETER_INDEX_2, user.getLastName());
            statement.setString(PARAMETER_INDEX_3, user.getEmail());
            statement.setString(PARAMETER_INDEX_4, user.getPassword());
            statement.setString(PARAMETER_INDEX_5, user.getRole().toString());
            return statement;
        }, keyHolder);
                Optional<User> optionalUser = findById((Long) Objects.requireNonNull(keyHolder.getKeys()).get("id"));
                return optionalUser;
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            Optional<User> optionalUser = jdbcTemplate.queryForObject(SELECT_BY_ID, this::mapRow, id);
            return optionalUser;
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public List<Optional<User>> findAll() {
        List<Optional<User>> list = jdbcTemplate.query(SELECT_ALL, this::mapRow);
        return list;
    }

    @Override
    public Optional<User> update(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("first_name", user.getFirstName());
        params.put("last_name", user.getLastName());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        params.put("role", user.getRole().toString());
        params.put("id", user.getId());
        namedParameterJdbcTemplate.update(UPDATE, params);
            Optional<User> optionalUser = findById(user.getId());
            return optionalUser;
    }

    @Override
    public boolean delete(User user) {
        int update = jdbcTemplate.update(DELETE, user.getId());
        return update == 1;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            Optional<User> optionalUser = jdbcTemplate.queryForObject(SELECT_BY_EMAIL, this::mapRow, email);
            return optionalUser;
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Optional<User>> findByLastName(String lastName) {
        List<Optional<User>> list = jdbcTemplate.query(SELECT_BY_LAST_NAME, this::mapRow, lastName);
        return list;
    }

    @Override
    public Long countAll() {
        try {
            Long count = jdbcTemplate.queryForObject(SELECT_COUNT, (rs, rowNum) -> rs.getLong(COLUMN_INDEX_1));
            return count;
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new EmptyRepositoryException("Not found any users");
        }
    }
}
