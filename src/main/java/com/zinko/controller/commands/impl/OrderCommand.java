package com.zinko.controller.commands.impl;

import com.zinko.controller.commands.Command;
import com.zinko.service.OrderService;
import com.zinko.service.dto.OrderDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("order")
@RequiredArgsConstructor
public class OrderCommand implements Command {
    private final OrderService orderService;

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.valueOf(req.getParameter("id"));
        OrderDto order = orderService.findById(id);
        req.setAttribute("order", order);
        return "jsp/order.jsp";
    }
}
