package com.zinko.controller.commands.impl;

import com.zinko.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

public class UserEditFormCommand extends AbstractUserCommand{
    public UserEditFormCommand(UserService userService) {
        super(userService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        req.setAttribute("user", userService.findById(Long.valueOf(req.getParameter("id"))));
        return "jsp/userEdit.jsp";
    }
}
