package com.zinko.controller.commands.impl;

import com.zinko.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("order")
public class OrderCommand extends AbstractOrderCommand{
    public OrderCommand(OrderService orderService) {
        super(orderService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        req.setAttribute("order", orderService.findById(Long.valueOf(req.getParameter("id"))));
        return "jsp/order.jsp";
    }
}
