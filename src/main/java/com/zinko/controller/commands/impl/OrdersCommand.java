package com.zinko.controller.commands.impl;

import com.zinko.controller.commands.Command;
import com.zinko.service.OrderService;
import com.zinko.service.dto.OrderDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller("orders")
@RequiredArgsConstructor
public class OrdersCommand implements Command {

    private final OrderService orderService;

    @Override
    public String execute(HttpServletRequest req) {
        List<OrderDto> orders = orderService.findAll();
        req.setAttribute("orders", orders);
        return "jsp/orders.jsp";
    }
}
