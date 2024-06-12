package com.zinko.service.serviceMapper;

import com.zinko.data.entity.Book;
import com.zinko.data.entity.Order;
import com.zinko.data.entity.OrderItem;
import com.zinko.data.entity.User;
import com.zinko.service.dto.BookDto;
import com.zinko.service.dto.OrderDto;
import com.zinko.service.dto.OrderItemDto;
import com.zinko.service.dto.UserDto;

public interface ServiceMapper {
    BookDto toDto(Book book);
    Book toBook(BookDto bookDto);
    UserDto toDto(User user);
    User toUser(UserDto userDto);
    OrderDto toDto(Order order);
    OrderItemDto toDto(OrderItem orderItem);

}
