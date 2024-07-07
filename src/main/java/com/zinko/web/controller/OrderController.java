package com.zinko.web.controller;

import com.zinko.data.entity.Status;
import com.zinko.service.BookService;
import com.zinko.service.OrderService;
import com.zinko.service.dto.order.OrderDto;
import com.zinko.service.dto.order.OrderGetAllDto;
import com.zinko.service.dto.userDto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final BookService bookService;

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        OrderDto order = orderService.findById(id);
        model.addAttribute("order", order);
        return "order";
    }

    @GetMapping("/user_id/{id}")
    public String getByUserId(@PathVariable("id") Long id, Model model) {
        List<OrderDto> orders = orderService.findByUserId(id);
        model.addAttribute("orders", orders);
        return "ordersByUserId";
    }

    @GetMapping("/my_orders")
    public String getMyOrders(@SessionAttribute("user") UserDto user, Model model) {
        List<OrderDto> orders = orderService.findByUserId(user.getId());
        model.addAttribute("orders", orders);
        return "ordersByUserId";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        List<OrderGetAllDto> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/create")
    public String create() {
        return "orderCreate";
    }

    @PostMapping("/create")
    public String create(OrderDto orderDto) {
        OrderDto order = orderService.create(orderDto);
        return "redirect: /orders/" + order.getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        orderService.delete(id);
        return "redirect: /orders/all";
    }

    @PostMapping("/update")
    public String update(OrderDto orderDto) {
        OrderDto order = orderService.update(orderDto);
        return "redirect: /orders/" + order.getId();
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        OrderDto order = orderService.findById(id);
        model.addAttribute("order", order);
        return "orderUpdate";
    }

    @GetMapping("/cart")
    public String cart(@SessionAttribute("user") UserDto user, Model model) {
        OrderDto cart = orderService.getCart(user);
        model.addAttribute("order", cart);
        return "cart";
    }

    @PostMapping("/cart/add_book/{id}")
    public String addBook(@SessionAttribute("user") UserDto user, @PathVariable("id") Long id) {
        OrderDto cart = orderService.getCart(user);
        orderService.addBook(cart, bookService.findById(id));
        return "redirect: /orders/cart";
    }

    @PostMapping("/cart/delete_book/{id}")
    public String deleteBook(@SessionAttribute("user") UserDto user, @PathVariable("id") Long id) {
        OrderDto cart = orderService.getCart(user);
        orderService.deleteBook(cart, bookService.findById(id));
        return "redirect: /orders/cart";
    }

    @PostMapping("/cart/checkout")
    public String checkout(@SessionAttribute("user") UserDto user) {
        OrderDto cart = orderService.getCart(user);
        cart.setStatus(Status.PROCESSED);
        orderService.update(cart);
        return "redirect: /orders/my_orders";
    }

}
