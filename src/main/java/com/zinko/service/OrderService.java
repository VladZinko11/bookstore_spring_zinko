package com.zinko.service;

import com.zinko.service.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> findAll();
    List<OrderDto> findByUserId(Long id);

    OrderDto findById(Long id);
}
