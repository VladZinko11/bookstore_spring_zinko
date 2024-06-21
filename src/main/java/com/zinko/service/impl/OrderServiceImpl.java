package com.zinko.service.impl;

import com.zinko.data.entity.Order;
import com.zinko.data.entity.OrderItem;
import com.zinko.data.entity.User;
import com.zinko.data.repository.OrderItemRepository;
import com.zinko.data.repository.OrderRepository;
import com.zinko.data.repository.UserRepository;
import com.zinko.service.OrderService;
import com.zinko.service.dto.OrderDto.OrderDto;
import com.zinko.service.dto.OrderDto.OrderGetAllDto;
import com.zinko.service.dto.bookDto.BookDto;
import com.zinko.service.dto.userDto.UserDto;
import com.zinko.service.exception.EmptyRepositoryException;
import com.zinko.service.exception.InvalidIndexException;
import com.zinko.service.serviceMapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ServiceMapper serviceMapper;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderDto create(OrderDto orderDto) {
        log.debug("OrderService create method call");
        Order order = orderRepository.save(serviceMapper.toOrderFromOrderDto(orderDto));
        return serviceMapper.toOrderDtoFromOrder(order);
    }

    @Override
    public List<OrderGetAllDto> findAll() {
        log.debug("OrderService findAll method call");
        List<OrderGetAllDto> list = orderRepository.findAll().stream()
                .map(serviceMapper::toOrderGetAllDtoFromOrder)
                .toList();
        if (list.isEmpty()) {
            throw new EmptyRepositoryException("There are no orders yet");
        }
        return list;
    }

    @Override
    public List<OrderDto> findByUserId(Long id) {
        log.debug("OrderService findByUserId method call with userId: {}", id);
        User user = userRepository.findById(id).orElseThrow(
                () -> new InvalidIndexException("Not found user with id: " + id));
        List<OrderDto> list = orderRepository.findByUser(user).stream()
                .map(serviceMapper::toOrderDtoFromOrder)
                .toList();
        if (list.isEmpty()) {
            throw new EmptyRepositoryException("There are no orders yet");
        }
        return list;
    }

    @Override
    public OrderDto findById(Long id) {
        log.debug("OrderService findById method call with id: {}", id);
        return serviceMapper.toOrderDtoFromOrder(orderRepository.findById(id)
                .orElseThrow(() -> new InvalidIndexException("Not found order with id: " + id)));
    }

    @Override
    public OrderDto update(OrderDto orderDto) {
        log.debug("OrderService update method call for order with id: {}", orderDto.getId());
        Order order = orderRepository.save(serviceMapper.toOrderFromOrderDto(orderDto));
        return serviceMapper.toOrderDtoFromOrder(order);
    }

    @Override
    public void delete(Long id) {
        log.debug("OrderService method delete call with id: {}", id);
        if (!orderRepository.delete(id)) {
            throw new InvalidIndexException("Not found order with id " + id);
        }
    }

    @Override
    public OrderDto getBasket(UserDto userDto) {
        Order basket = orderRepository.getBasket(serviceMapper.toUserFromUserDto(userDto));
        if (basket == null) {
            throw new InvalidIndexException("Not found basket");
        }
        return serviceMapper.toOrderDtoFromOrder(basket);
    }

    @Override
    public void deleteBook(OrderDto basket, BookDto bookDto) {
        List<OrderItem> list = orderItemRepository.findByOrder(serviceMapper.toOrderFromOrderDto(basket)).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        for (OrderItem orderItem : list) {
            if (Objects.equals(orderItem.getBook().getId(), bookDto.getId())) {
                orderItem.setQuantity(orderItem.getQuantity() - 1);
                orderItemRepository.save(orderItem);
                return;
            }
        }
    }

    @Override
    public void addBook(OrderDto basket, BookDto bookDto) {
        List<OrderItem> list = orderItemRepository.findByOrder(serviceMapper.toOrderFromOrderDto(basket)).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        for (OrderItem orderItem : list) {
            if (Objects.equals(orderItem.getBook().getId(), bookDto.getId())) {
                orderItem.setQuantity(orderItem.getQuantity() + 1);
                orderItemRepository.save(orderItem);
                return;
            }
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(serviceMapper.toBookFromBookDto(bookDto));
        orderItem.setQuantity(1);
        orderItem.setOrder(serviceMapper.toOrderFromOrderDto(basket));
        orderItemRepository.save(orderItem);
    }
}
