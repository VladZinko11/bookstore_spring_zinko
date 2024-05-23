package com.zinko.data.dao.impl;

import com.zinko.data.dao.UserDao;
import com.zinko.data.dao.entity.User;
import com.zinko.data.dao.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final int PARAMETER_INDEX_6 = 6;
    private static final String SELECT_COUNT = "SELECT COUNT(*) FROM public.user WHERE deleted=false";
    private static final String SELECT_BY_LAST_NAME = "SELECT u.id, u.first_name, u.last_name, u.email, u.password, e.role FROM public.user AS u JOIN public.enum_role AS e ON u.id_enum_role=e.id WHERE u.last_name=? AND u.deleted=false";
    private static final String SELECT_BY_EMAIL = "SELECT u.id, u.first_name, u.last_name, u.email, u.password, e.role FROM public.user AS u JOIN public.enum_role AS e ON u.id_enum_role=e.id WHERE u.email=? AND u.deleted=false";
    private static final String DELETE = "UPDATE public.user SET deleted=true WHERE id=?";
    private static final String UPDATE = "UPDATE public.user SET first_name=:first_name, last_name=:last_name, email=:email, password=:password, id_enum_role=(SELECT id FROM enum_role WHERE role=:role) WHERE id=:id AND deleted=false";
    private static final String SELECT_ALL = "SELECT u.id, u.first_name, u.last_name, u.email, u.password, e.role FROM public.user AS u JOIN public.enum_role AS e ON u.id_enum_role=e.id WHERE u.deleted=false ORDER BY u.id";
    private static final String SELECT_BY_ID = "SELECT u.id, u.first_name, u.last_name, u.email, u.password, e.role FROM public.user AS u JOIN public.enum_role AS e ON u.id_enum_role=e.id WHERE u.id=? AND u.deleted=false";
    private static final String CREATE = "INSERT INTO public.user (first_name, last_name, email, password, id_enum_role, deleted) VALUES (?, ?, ?, ?, (SELECT id FROM enum_role WHERE role=?), false)";
    private static final String SELECT_ID_ROLE = "SELECT id FROM enum_role WHERE role=?";

    private User mapRow(ResultSet resultSet, int num) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(COLUMN_INDEX_1));
        user.setFirstName(resultSet.getString(COLUMN_INDEX_2));
        user.setLastName(resultSet.getString(COLUMN_INDEX_3));
        user.setEmail(resultSet.getString(COLUMN_INDEX_4));
        user.setPassword(resultSet.getString(COLUMN_INDEX_5));
        user.setRole(Role.valueOf(resultSet.getString(COLUMN_INDEX_6)));
        return user;
    }

    @Override
    public User create(User user) {
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
        if (update == 1) {
            User newUser = findById((Long) keyHolder.getKey());
            return newUser;
        } else return null;
    }

    @Override
    public User findById(Long id) {
        User user = jdbcTemplate.queryForObject(SELECT_BY_ID, this::mapRow, id);
        return user;
    }


    @Override
    public List<User> findAll() {
        List<User> list = jdbcTemplate.query(SELECT_ALL, this::mapRow);
        return list;
    }

    @Override
    public User update(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("first_name", user.getFirstName());
        params.put("last_name", user.getLastName());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());
        params.put("role", user.getRole().toString());
        params.put("id", user.getId());
        int update = namedParameterJdbcTemplate.update(UPDATE, params);
        if(update==1) {
            User updatedUser = findById(user.getId());
            return updatedUser;
        } else return null;
    }

    @Override
    public boolean delete(User user) {
        int update = jdbcTemplate.update(DELETE, user.getId());
        return update==1;
    }

    @Override
    public User findByEmail(String email) {
        User user = jdbcTemplate.queryForObject(SELECT_BY_EMAIL, this::mapRow, email);
        return user;
    }

    @Override
    public List<User> findByLastName(String lastName) {
        List<User> list = jdbcTemplate.query(SELECT_BY_LAST_NAME, this::mapRow, lastName);
        return list;
    }

    @Override
    public Long countAll() {
        Long count = jdbcTemplate.queryForObject(SELECT_COUNT, (rs, rowNum) -> rs.getLong(COLUMN_INDEX_1));
        return count;
    }
}
