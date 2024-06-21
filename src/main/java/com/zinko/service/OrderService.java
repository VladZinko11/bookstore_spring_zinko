package com.zinko.service;

import com.zinko.service.dto.OrderDto.OrderDto;
import com.zinko.service.dto.OrderDto.OrderGetAllDto;
import com.zinko.service.dto.bookDto.BookDto;
import com.zinko.service.dto.userDto.UserDto;

import java.util.List;

public interface OrderService {

    OrderDto create(OrderDto orderDto);

    List<OrderGetAllDto> findAll();

    List<OrderDto> findByUserId(Long key);

    OrderDto findById(Long id);

    OrderDto update(OrderDto orderDto);

    void delete(Long id);

    OrderDto getBasket(UserDto userDto);

    void addBook(OrderDto basket, BookDto bookDto);

    void deleteBook(OrderDto basket, BookDto bookDto);
}
