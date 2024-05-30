package com.zinko.data.dao.impl;

import com.zinko.data.dao.OrderItemDao;
import com.zinko.data.dao.dto.OrderItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderItemDaoImpl implements OrderItemDao {

    private static final int COLUMN_INDEX_1 = 1;
    private static final int COLUMN_INDEX_2 = 2;
    private static final int COLUMN_INDEX_3 = 3;
    private static final int COLUMN_INDEX_4 = 4;
    private static final String INSERT = "INSERT INTO public.order_item (book_id, quantity, price, order_id, deleted) VALUES (:book_id, :quantity, :price, :order_id, false)";
    private static final String SELECT_BY_ORDER_ID = "SELECT order_id, book_id, quantity, price FROM public.order_item WHERE order_id=? AND deleted=false";
    private static final String SELECT_BY_ORDER_ID_AND_BOOK_ID = "SELECT order_id, book_id, quantity, price FROM public.order_item WHERE order_id=? AND book_id=? AND deleted=false";
    private static final String SELECT_ALL = "SELECT order_id, book_id, quantity, price FROM public.order_item WHERE deleted=false";
    private static final String UPDATE = "UPDATE public.order_item SET quantity=:quantity, price=:price WHERE order_id=:order_id AND book_id=:book_id AND deleted=false";
    private static final String DELETE = "UPDATE public.order_item SET deleted=true WHERE order_id=? AND book_id=? AND deleted=false";


    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Optional<OrderItemDto> mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setOrderId(resultSet.getLong(COLUMN_INDEX_1));
        orderItemDto.setBookID(resultSet.getLong(COLUMN_INDEX_2));
        orderItemDto.setQuantity(resultSet.getInt(COLUMN_INDEX_3));
        orderItemDto.setPrice(resultSet.getBigDecimal(COLUMN_INDEX_4));
        return Optional.ofNullable(orderItemDto);
    }

    private Optional<OrderItemDto> updateOrderItemWithNamedParameter(OrderItemDto orderItemDto, String sql) {
        Map<String, Object> params = new HashMap<>();
        params.put("order_id", orderItemDto.getOrderId());
        params.put("book_id", orderItemDto.getBookID());
        params.put("quantity", orderItemDto.getQuantity());
        params.put("price", orderItemDto.getPrice());
        namedParameterJdbcTemplate.update(sql, params);
            Optional<OrderItemDto> optionalOrderItemDto = findByBookIdFromOrder(orderItemDto.getOrderId(), orderItemDto.getBookID());
            return optionalOrderItemDto;
    }

    @Override
    public Optional<OrderItemDto> create(OrderItemDto orderItemDto) {
        Optional<OrderItemDto> optionalOrderItemDto = updateOrderItemWithNamedParameter(orderItemDto, INSERT);
        return optionalOrderItemDto;
    }

    @Override
    public List<Optional<OrderItemDto>> findByOrderId(Long id) {
        List<Optional<OrderItemDto>> list = jdbcTemplate.query(SELECT_BY_ORDER_ID, this::mapRow, id);
        return list;
    }

    @Override
    public Optional<OrderItemDto> findByBookIdFromOrder(Long orderId, Long bookId) {
        try {
            Optional<OrderItemDto> optionalOrderItemDto = jdbcTemplate.queryForObject(SELECT_BY_ORDER_ID_AND_BOOK_ID, this::mapRow, orderId, bookId);
            return optionalOrderItemDto;
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Optional<OrderItemDto>> findAll() {
        List<Optional<OrderItemDto>> list = jdbcTemplate.query(SELECT_ALL, this::mapRow);
        return list;
    }

    @Override
    public Optional<OrderItemDto> update(OrderItemDto orderItemDto) {
        Optional<OrderItemDto> optionalOrderItemDto = updateOrderItemWithNamedParameter(orderItemDto, UPDATE);
        return optionalOrderItemDto;
    }

    @Override
    public Boolean delete(OrderItemDto orderItemDto) {
        int update = jdbcTemplate.update(DELETE, orderItemDto.getOrderId(), orderItemDto.getBookID());
        return update==1;
    }
}
