package com.zinko.service.impl;

import com.zinko.data.repository.OrderRepository;
import com.zinko.exception.EmptyRepositoryException;
import com.zinko.exception.InvalidIndexException;
import com.zinko.service.OrderService;
import com.zinko.service.dto.OrderDto;
import com.zinko.service.serviceMapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public List<OrderDto> findAll() {
        List<OrderDto> list = orderRepository.findAll().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this.serviceMapper::toDto)
                .toList();
        if (list.isEmpty()) {
            throw new EmptyRepositoryException("Not found any orders");
        } else {
            return list;
        }
    }

    @Override
    public List<OrderDto> findByUserId(Long id) {
        List<OrderDto> list = orderRepository.findByUserId(id).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this.serviceMapper::toDto)
                .toList();
        if (list.isEmpty()) {
            throw new EmptyRepositoryException("Not found any orders from users with id: " + id);
        } else {
            return list;
        }
    }

    @Override
    public OrderDto findById(Long id) {
        OrderDto orderDto = serviceMapper.toDto(orderRepository.findById(id)
                .orElseThrow(()->new InvalidIndexException("Not found order with id: " + id)));
        return orderDto;
    }
}
