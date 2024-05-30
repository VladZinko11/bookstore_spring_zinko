package com.zinko.controller.commands.impl;

import com.zinko.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("orders_by_user_id")
public class OrdersByUserIdCommand extends AbstractOrderCommand{
    public OrdersByUserIdCommand(OrderService orderService) {
        super(orderService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        req.setAttribute("orders", orderService.findByUserId(Long.valueOf(req.getParameter("id"))));
        return "jsp/ordersByUserId.jsp";
    }
}
