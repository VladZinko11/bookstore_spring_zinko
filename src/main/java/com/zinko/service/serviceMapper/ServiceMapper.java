package com.zinko.service.serviceMapper;

import com.zinko.data.entity.Book;
import com.zinko.data.entity.Order;
import com.zinko.data.entity.OrderItem;
import com.zinko.data.entity.User;
import com.zinko.service.dto.bookDto.BookCreateDto;
import com.zinko.service.dto.bookDto.BookDto;
import com.zinko.service.dto.order.OrderDto;
import com.zinko.service.dto.order.OrderGetAllDto;
import com.zinko.service.dto.order.OrderItemDto;
import com.zinko.service.dto.userDto.UserCreateDto;
import com.zinko.service.dto.userDto.UserDto;

public interface ServiceMapper {
    BookDto toBookDtoFromBook(Book book);

    Book toBookFromBookDto(BookDto bookDto);

    UserDto toUserDtoFromUser(User user);

    User toUserFromUserDto(UserDto userDto);

    OrderDto toOrderDtoFromOrder(Order order);

    OrderItemDto toOrderItemDtoFromOrderItem(OrderItem orderItem);

    UserDto toUserDtoFromUserCreateDto(UserCreateDto userCreateDto);

    BookDto toBookDtoFromBookCreateDto(BookCreateDto bookCreateDto);

    OrderGetAllDto toOrderGetAllDtoFromOrder(Order order);

    Order toOrderFromOrderDto(OrderDto orderDto);

    OrderItem toOrderItemFromOrderItemDto(OrderItemDto orderItemDto);
}
