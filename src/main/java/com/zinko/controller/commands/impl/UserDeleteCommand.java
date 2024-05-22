package com.zinko.controller.commands.impl;

import com.zinko.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("user_delete")
public class UserDeleteCommand extends AbstractUserCommand {
    public UserDeleteCommand(UserService userService) {
        super(userService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        userService.delete(Long.valueOf(req.getParameter("id")));
        req.setAttribute("users", userService.findAll());
        return "jsp/users.jsp";
    }
}
