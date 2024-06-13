package com.zinko.controller.commands.impl;

import com.zinko.controller.commands.Command;
import com.zinko.service.OrderService;
import com.zinko.service.dto.OrderDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller("orders_by_user_id")
@RequiredArgsConstructor
public class OrdersByUserIdCommand implements Command {

    private final OrderService orderService;

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.valueOf(req.getParameter("id"));
        List<OrderDto> orders = orderService.findByUserId(id);
        req.setAttribute("orders", orders);
        return "jsp/ordersByUserId.jsp";
    }
}
