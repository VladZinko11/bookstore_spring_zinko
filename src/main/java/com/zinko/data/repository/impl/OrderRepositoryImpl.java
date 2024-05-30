package com.zinko.data.repository.impl;

import com.zinko.data.dao.BookDao;
import com.zinko.data.dao.OrderDao;
import com.zinko.data.dao.OrderItemDao;
import com.zinko.data.dao.UserDao;
import com.zinko.data.dao.dto.OrderDto;
import com.zinko.data.dao.dto.OrderItemDto;
import com.zinko.data.dao.entity.Order;
import com.zinko.data.dao.entity.OrderItem;
import com.zinko.data.dao.entity.User;
import com.zinko.data.dataMapper.DataMapper;
import com.zinko.data.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final UserDao userDao;
    private final BookDao bookDao;
    private final DataMapper dataMapper;

    private Optional<Order> orderDtoToOrder(OrderDto orderDto) {
        List<Optional<OrderItemDto>> optionalList = orderItemDao.findByOrderId(orderDto.getId());
        List<OrderItem> orderItemList =
                optionalList.stream()
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .filter(orderItemDto -> bookDao.findBookById(orderItemDto.getBookID()).isPresent())
                        .map(orderItemDto -> dataMapper.getOrderItem(orderItemDto, bookDao.findBookById(orderItemDto.getBookID()).get()))
                        .toList();
        Optional<User> optionalUser = userDao.findById(orderDto.getUserId());
        Order order = dataMapper.getOrder(orderDto, optionalUser.orElse(new User()), orderItemList);
        return Optional.ofNullable(order);
    }

    @Override
    public List<Optional<Order>> findAll() {
        List<Optional<OrderDto>> orderDtoList = orderDao.findAll();
        List<Optional<Order>> orderList = orderDtoList.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::orderDtoToOrder)
                .toList();
        return orderList;
    }

    @Override
    public List<Optional<Order>> findByUserId(Long id) {
        List<Optional<OrderDto>> orderDtoList = orderDao.findOrdersByUserId(id);
        List<Optional<Order>> orderList = orderDtoList.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::orderDtoToOrder)
                .toList();
        return orderList;
    }

    @Override
    public Optional<Order> findById(Long id) {
        Optional<OrderDto> optionalOrderDto = orderDao.findById(id);
        if(optionalOrderDto.isPresent()) {
            Optional<Order> optionalOrder = orderDtoToOrder(optionalOrderDto.get());
            return optionalOrder;
        }
        return Optional.empty();
    }
}
