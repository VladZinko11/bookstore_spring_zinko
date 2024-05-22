package com.zinko.controller.commands.impl;

import com.zinko.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("users")
public class UsersCommand extends AbstractUserCommand {

    public UsersCommand(UserService userService) {
        super(userService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        req.setAttribute("users", userService.findAll());
        return "jsp/users.jsp";
    }
}
