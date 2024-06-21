package com.zinko.service.serviceMapper.impl;

import com.zinko.data.entity.Book;
import com.zinko.data.entity.Order;
import com.zinko.data.entity.OrderItem;
import com.zinko.data.entity.User;
import com.zinko.service.dto.OrderDto.OrderDto;
import com.zinko.service.dto.OrderDto.OrderGetAllDto;
import com.zinko.service.dto.OrderDto.OrderItemDto;
import com.zinko.service.dto.bookDto.BookCreateDto;
import com.zinko.service.dto.bookDto.BookDto;
import com.zinko.service.dto.userDto.UserCreateDto;
import com.zinko.service.dto.userDto.UserDto;
import com.zinko.service.serviceMapper.ServiceMapper;
import org.springframework.stereotype.Component;

@Component
public class ServiceMapperImpl implements ServiceMapper {

    @Override
    public BookDto toBookDtoFromBook(Book book) {
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
    public Book toBookFromBookDto(BookDto bookDto) {
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
    public UserDto toUserDtoFromUser(User user) {
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
    public User toUserFromUserDto(UserDto userDto) {
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
    public OrderDto toOrderDtoFromOrder(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserDto(toUserDtoFromUser(order.getUser()));
        if (order.getOrderItems() != null) {
            orderDto.setOrderItemsDto(order.getOrderItems().stream()
                    .map(this::toOrderItemDtoFromOrderItem)
                    .toList());
        }
        orderDto.setStatus(order.getStatus());
        orderDto.setCost(order.getCost());
        return orderDto;
    }

    @Override
    public OrderItemDto toOrderItemDtoFromOrderItem(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setBookDto(toBookDtoFromBook(orderItem.getBook()));
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setQuantity(orderItem.getQuantity());
        return orderItemDto;
    }

    @Override
    public UserDto toUserDtoFromUserCreateDto(UserCreateDto userCreateDto) {
        UserDto userDto = new UserDto();
        userDto.setEmail(userCreateDto.getEmail());
        userDto.setPassword(userCreateDto.getPassword());
        return userDto;
    }

    @Override
    public BookDto toBookDtoFromBookCreateDto(BookCreateDto bookCreateDto) {
        BookDto bookDto = new BookDto();
        bookDto.setAuthor(bookCreateDto.getAuthor());
        bookDto.setTitle(bookCreateDto.getTitle());
        bookDto.setIsbn(bookCreateDto.getIsbn());
        bookDto.setPublicationDate(bookCreateDto.getPublicationDate());
        bookDto.setPrice(bookCreateDto.getPrice());
        return bookDto;
    }

    @Override
    public OrderGetAllDto toOrderGetAllDtoFromOrder(Order order) {
        OrderGetAllDto orderGetAllDto = new OrderGetAllDto();
        orderGetAllDto.setId(order.getId());
        orderGetAllDto.setUserDto(toUserDtoFromUser(order.getUser()));
        orderGetAllDto.setStatus(order.getStatus());
        return orderGetAllDto;
    }

    @Override
    public Order toOrderFromOrderDto(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setUser(toUserFromUserDto(orderDto.getUserDto()));
        if (orderDto.getOrderItemsDto() != null) {
            order.setOrderItems(orderDto.getOrderItemsDto().stream()
                    .map(this::toOrderItemFromOrderItemDto)
                    .toList());
        }
        order.setStatus(orderDto.getStatus());
        return order;
    }

    @Override
    public OrderItem toOrderItemFromOrderItemDto(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(toBookFromBookDto(orderItemDto.getBookDto()));
        orderItem.setQuantity(orderItemDto.getQuantity());
        return orderItem;
    }

}
