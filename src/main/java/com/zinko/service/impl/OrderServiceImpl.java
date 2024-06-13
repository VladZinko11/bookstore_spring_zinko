package com.zinko.service.impl;

import com.zinko.data.entity.User;
import com.zinko.data.repository.OrderRepository;
import com.zinko.data.repository.UserRepository;
import com.zinko.exception.EmptyRepositoryException;
import com.zinko.exception.InvalidIndexException;
import com.zinko.service.OrderService;
import com.zinko.service.dto.OrderDto;
import com.zinko.service.serviceMapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ServiceMapper serviceMapper;
    private final UserRepository userRepository;

    @Override
    public List<OrderDto> findAll() {
        log.debug("OrderService findAll method call");
        List<OrderDto> list = orderRepository.findAll().stream().map(serviceMapper::toDto).toList();
        if(list.isEmpty()) {
            throw new EmptyRepositoryException("There are no orders yet");
        }
        return list;
    }

    @Override
    public List<OrderDto> findByUserId(Long id) {
        log.debug("OrderService findByUserId method call with userId: {}", id);
        User user = userRepository.findById(id).orElseThrow(()-> new InvalidIndexException("Not found user with id: " + id));
        List<OrderDto> list = orderRepository.findByUser(user).stream().map(serviceMapper::toDto).toList();
        if(list.isEmpty()) {
            throw new EmptyRepositoryException("There are no orders yet");
        }
        return list;
    }

    @Override
    public OrderDto findById(Long id) {
        log.debug("OrderService findById method call with id: {}", id);
        return serviceMapper.toDto(orderRepository.findById(id)
                .orElseThrow(()-> new InvalidIndexException("Not found order with id: " + id)));
    }
}
