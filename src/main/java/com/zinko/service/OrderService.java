package com.zinko.service;

import com.zinko.service.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> findAll();

    List<OrderDto> findByUserId(Long key);

    OrderDto findById(Long id);
}
