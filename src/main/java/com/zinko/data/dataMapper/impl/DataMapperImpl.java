package com.zinko.data.dataMapper.impl;

import com.zinko.data.dao.dto.OrderDto;
import com.zinko.data.dao.dto.OrderItemDto;
import com.zinko.data.dao.entity.Book;
import com.zinko.data.dao.entity.Order;
import com.zinko.data.dao.entity.OrderItem;
import com.zinko.data.dao.entity.User;
import com.zinko.data.dataMapper.DataMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataMapperImpl implements DataMapper {
    @Override
    public Order getOrder(OrderDto orderDto, User user, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setStatus(orderDto.getStatus());
        order.setId(orderDto.getId());
        order.setUser(user);
        order.setOrderItems(orderItems);
        return order;
    }

    @Override
    public OrderItem getOrderItem(OrderItemDto orderItemDto, Book book) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(book);
        orderItem.setQuantity(orderItemDto.getQuantity());
        return orderItem;
    }
}

