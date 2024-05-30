package com.zinko.data.dao;

import com.zinko.data.dao.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Optional<OrderDto> create(OrderDto order);
    Optional<OrderDto> findById(Long id);
    List<Optional<OrderDto>> findAll();
    Optional<OrderDto> update(OrderDto order);
    Boolean delete(OrderDto order);
    List<Optional<OrderDto>> findOrdersByUserId(Long id);

}
