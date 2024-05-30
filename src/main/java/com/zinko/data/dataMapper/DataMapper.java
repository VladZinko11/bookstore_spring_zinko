package com.zinko.data.dataMapper;

import com.zinko.data.dao.dto.OrderDto;
import com.zinko.data.dao.dto.OrderItemDto;
import com.zinko.data.dao.entity.Book;
import com.zinko.data.dao.entity.Order;
import com.zinko.data.dao.entity.OrderItem;
import com.zinko.data.dao.entity.User;

import java.util.List;

public interface DataMapper {
    Order getOrder(OrderDto orderDto, User user, List<OrderItem> orderItems);
    OrderItem getOrderItem(OrderItemDto orderItemDto, Book book);
}
