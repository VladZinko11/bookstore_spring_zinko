package com.zinko.service.serviceMapper.impl;

import com.zinko.data.dao.entity.Book;
import com.zinko.data.dao.entity.Order;
import com.zinko.data.dao.entity.OrderItem;
import com.zinko.data.dao.entity.User;
import com.zinko.service.dto.BookDto;
import com.zinko.service.dto.OrderDto;
import com.zinko.service.dto.OrderItemDto;
import com.zinko.service.dto.UserDto;
import com.zinko.service.serviceMapper.ServiceMapper;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapperImpl implements ServiceMapper {

    @Override
    public BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setAuthor(book.getAuthor());
        bookDto.setTitle(book.getTitle());
        bookDto.setId(book.getId());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPublicationDate(book.getPublicationDate());
        bookDto.setPrice(book.getPrice());
        return bookDto;
    }

    @Override
    public Book toBook(BookDto bookDto) {
        Book book = new Book();
        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());
        book.setId(bookDto.getId());
        book.setIsbn(bookDto.getIsbn());
        book.setPublicationDate(bookDto.getPublicationDate());
        book.setPrice(bookDto.getPrice());
        return book;
    }

    @Override
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        return userDto;
    }

    @Override
    public User toUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        return user;
    }

    @Override
    public OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserDto(toDto(order.getUser()));
        orderDto.setOrderItemsDto(order.getOrderItems().stream().map(this::toDto).toList());
        orderDto.setStatus(order.getStatus());
        orderDto.setCost(order.getCost());
        return orderDto;
    }

    @Override
    public OrderItemDto toDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setBookDto(toDto(orderItem.getBook()));
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setQuantity(orderItem.getQuantity());
        return orderItemDto;
    }


}
