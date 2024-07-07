package com.zinko.service.impl;

import com.zinko.data.entity.Order;
import com.zinko.data.entity.OrderItem;
import com.zinko.data.entity.Status;
import com.zinko.data.entity.User;
import com.zinko.data.repository.OrderItemRepository;
import com.zinko.data.repository.OrderRepository;
import com.zinko.data.repository.UserRepository;
import com.zinko.service.OrderService;
import com.zinko.service.dto.bookDto.BookDto;
import com.zinko.service.dto.order.OrderDto;
import com.zinko.service.dto.order.OrderGetAllDto;
import com.zinko.service.dto.userDto.UserDto;
import com.zinko.service.exception.EmptyRepositoryException;
import com.zinko.service.exception.InvalidIndexException;
import com.zinko.service.serviceMapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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
                .filter(order -> !order.getStatus().equals(Status.CART))
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
    public OrderDto getCart(UserDto userDto) {
        log.debug("OrderService method getCart call for user with id: {}", userDto.getId());
        User user = serviceMapper.toUserFromUserDto(userDto);
        List<Order> orders = orderRepository.findByUser(user);
        Order cart = orders.stream()
                .filter(order -> order.getStatus().toString().equals("CART"))
                .findFirst()
                .orElse(null);
        if (cart == null) {
            cart = new Order();
            cart.setStatus(Status.CART);
            cart.setUser(user);
            orderRepository.save(cart);
        }
        return serviceMapper.toOrderDtoFromOrder(cart);
    }

    @Override
    public void deleteBook(OrderDto orderDto, BookDto bookDto) {
        log.debug("OrderService method deleteBook call for order: {} and add book: {}", orderDto.toString(), bookDto.toString());
        List<OrderItem> list = orderItemRepository.findByOrder(serviceMapper.toOrderFromOrderDto(orderDto));
        for (OrderItem orderItem : list) {
            if (Objects.equals(orderItem.getBook().getId(), bookDto.getId())) {
                orderItem.setQuantity(orderItem.getQuantity() - 1);
                orderItem.setPrice(BigDecimal.valueOf(orderItem.getPrice().longValue() - bookDto.getPrice().longValue()));
                orderDto.setCost(BigDecimal.valueOf(orderDto.getCost().longValue() - bookDto.getPrice().longValue()));
                orderRepository.save(serviceMapper.toOrderFromOrderDto(orderDto));
                if (orderItem.getQuantity() == 0) {
                    orderItemRepository.delete(orderItem.getId());
                }
                return;
            }
        }
    }

    @Override
    public void addBook(OrderDto orderDto, BookDto bookDto) {
        log.debug("OrderService method addBook call for order: {} and add book: {}", orderDto.toString(), bookDto.toString());
        List<OrderItem> list = orderItemRepository.findByOrder(serviceMapper.toOrderFromOrderDto(orderDto));
        for (OrderItem orderItem : list) {
            if (Objects.equals(orderItem.getBook().getId(), bookDto.getId())) {
                orderItem.setQuantity(orderItem.getQuantity() + 1);
                orderItem.setPrice(BigDecimal.valueOf(bookDto.getPrice().longValue() + orderItem.getPrice().longValue()));
                if (orderDto.getCost() == null) {
                    orderDto.setCost(bookDto.getPrice());
                } else {
                    orderDto.setCost(BigDecimal.valueOf(orderDto.getCost().longValue() + bookDto.getPrice().longValue()));
                }
                log.debug("order cost: {}", orderDto.getCost());
                orderRepository.save(serviceMapper.toOrderFromOrderDto(orderDto));
                orderItemRepository.save(orderItem);
                return;
            }
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(serviceMapper.toBookFromBookDto(bookDto));
        orderItem.setQuantity(1);
        orderItem.setPrice(bookDto.getPrice());
        orderItem.setOrder(serviceMapper.toOrderFromOrderDto(orderDto));
        if (orderDto.getCost() == null) {
            orderDto.setCost(bookDto.getPrice());
        } else {
            orderDto.setCost(BigDecimal.valueOf(orderDto.getCost().longValue() + bookDto.getPrice().longValue()));
        }
        orderRepository.save(serviceMapper.toOrderFromOrderDto(orderDto));
        orderItemRepository.save(orderItem);
    }
}
