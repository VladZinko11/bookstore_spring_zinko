package com.zinko.service.serviceMapper.impl;

import com.zinko.data.entity.Book;
import com.zinko.data.entity.Order;
import com.zinko.data.entity.OrderItem;
import com.zinko.data.entity.User;
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
        return BookDto.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .title(book.getTitle())
                .publicationDate(book.getPublicationDate())
                .price(book.getPrice())
                .build();
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
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
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
        OrderDto orderDto = OrderDto.builder()
                .id(order.getId())
                .userDto(toDto(order.getUser()))
                .orderItemsDto(order.getOrderItems().stream().map(this::toDto).toList())
                .status(order.getStatus())
                .cost(order.getCost())
                .build();
        return orderDto;
    }

    @Override
    public OrderItemDto toDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = OrderItemDto.builder()
                .bookDto(toDto(orderItem.getBook()))
                .price(orderItem.getPrice())
                .quantity(orderItem.getQuantity())
                .build();
        return orderItemDto;
    }


}
