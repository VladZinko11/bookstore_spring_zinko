package com.zinko.controller.commands.impl;

import com.zinko.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component(value = "user_create_form")
public class UserCreateFormCommand extends AbstractUserCommand {
    public UserCreateFormCommand(UserService userService) {
        super(userService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/userCreate.jsp";
    }
}
