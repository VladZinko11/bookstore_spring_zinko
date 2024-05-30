package com.zinko.data.dao;

import com.zinko.data.dao.dto.OrderItemDto;

import java.util.List;
import java.util.Optional;

public interface OrderItemDao {

    Optional<OrderItemDto> create(OrderItemDto orderItemDto);
    List<Optional<OrderItemDto>> findByOrderId(Long id);

    Optional<OrderItemDto> findByBookIdFromOrder(Long orderId, Long BookId);
    List<Optional<OrderItemDto>> findAll();
    Optional<OrderItemDto> update(OrderItemDto orderItemDto);
    Boolean delete(OrderItemDto orderItemDto);
}
