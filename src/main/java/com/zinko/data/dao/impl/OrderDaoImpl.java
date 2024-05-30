package com.zinko.data.dao.impl;

import com.zinko.data.dao.OrderDao;
import com.zinko.data.dao.dto.OrderDto;
import com.zinko.data.dao.entity.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {

    private static final String INSERT = "INSERT INTO public.order (user_id, status_id, cost, deleted) values (?, (SELECT id FROM enum_status WHERE status=?), ?, false)";
    private static final String SELECT_BY_ID = "SELECT o.id, o.user_id, s.status, o.cost " +
            "FROM public.order AS o JOIN public.enum_status AS s ON o.status_id=s.id WHERE o.id=? AND o.deleted=false";
    private static final String SELECT_ALL = "SELECT o.id, o.user_id, s.status, o.cost FROM public.order AS o JOIN enum_status AS s ON o.status_id = s.id WHERE o.deleted=false";
    private static final String UPDATE = "UPDATE public.order SET status_id=(SELECT id FROM public.enum_status WHERE status=:status), cost=:cost WHERE id=:id AND deleted=false";
    private static final String DELETE = "UPDATE public.order SET deleted=true WHERE id=? AND deleted=false";
    private static final String SELECT_BY_USER_ID = "SELECT o.id, o.user_id, s.status, o.cost " +
            "FROM public.order AS o JOIN public.enum_status AS s ON o.status_id=s.id WHERE o.user_id=? AND o.deleted=false";

    private static final int PARAMETER_INDEX_1 = 1;
    private static final int PARAMETER_INDEX_2 = 2;
    private static final int PARAMETER_INDEX_3 = 3;

    private static final int COLUMN_INDEX_1 = 1;
    private static final int COLUMN_INDEX_2 = 2;
    private static final int COLUMN_INDEX_3 = 3;
    private static final int COLUMN_INDEX_4 = 4;

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Optional<OrderDto> mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(resultSet.getLong(COLUMN_INDEX_1));
        orderDto.setUserId(resultSet.getLong(COLUMN_INDEX_2));
        orderDto.setStatus(Status.valueOf(resultSet.getString(COLUMN_INDEX_3)));
        orderDto.setCost(resultSet.getBigDecimal(COLUMN_INDEX_4));
        return Optional.ofNullable(orderDto);
    }

    @Override
    public Optional<OrderDto> create(OrderDto order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int update = jdbcTemplate.update(con -> {
            PreparedStatement statement = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(PARAMETER_INDEX_1, order.getUserId());
            statement.setString(PARAMETER_INDEX_2, order.getStatus().toString());
            statement.setBigDecimal(PARAMETER_INDEX_3, order.getCost());
            return statement;
        }, keyHolder);
                Optional<OrderDto> optionalOrderDto = findById((Long) Objects.requireNonNull(keyHolder.getKeys()).get("id"));
                return optionalOrderDto;
    }

    @Override
    public Optional<OrderDto> findById(Long id) {
        try {
            Optional<OrderDto> optionalOrderDto = jdbcTemplate.queryForObject(SELECT_BY_ID, this::mapRow, id);
            return optionalOrderDto;
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Optional<OrderDto>> findAll() {
        List<Optional<OrderDto>> list = jdbcTemplate.query(SELECT_ALL, this::mapRow);
        return list;
    }

    @Override
    public Optional<OrderDto> update(OrderDto order) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", order.getId());
        params.put("status", order.getStatus());
        params.put("cost", order.getCost());
        int update = namedParameterJdbcTemplate.update(UPDATE, params);
            Optional<OrderDto> optionalOrderDto = findById(order.getId());
            return optionalOrderDto;
    }

    @Override
    public Boolean delete(OrderDto order) {
        int update = jdbcTemplate.update(DELETE, order.getId());
        return update == 1;
    }

    @Override
    public List<Optional<OrderDto>> findOrdersByUserId(Long id) {
        List<Optional<OrderDto>> list = jdbcTemplate.query(SELECT_BY_USER_ID, this::mapRow, id);
        return list;
    }
}
