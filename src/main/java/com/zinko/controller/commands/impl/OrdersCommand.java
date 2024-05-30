package com.zinko.controller.commands.impl;

import com.zinko.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("orders")
public class OrdersCommand extends AbstractOrderCommand {
    public OrdersCommand(OrderService orderService) {
        super(orderService);
    }

    @Override
    public String execute(HttpServletRequest req) {
    req.setAttribute("orders", orderService.findAll());
        return"jsp/orders.jsp";
}
}
