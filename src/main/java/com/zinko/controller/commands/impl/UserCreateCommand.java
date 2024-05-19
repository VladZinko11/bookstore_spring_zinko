package com.zinko.controller.commands.impl;

import com.zinko.service.UserService;
import com.zinko.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component(value = "user_create")
public class UserCreateCommand extends AbstractUserCommand{
    public UserCreateCommand(UserService userService) {
        super(userService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        UserDto userDto = new UserDto();
        userDto.setEmail(req.getParameter("email"));
        userDto.setPassword(req.getParameter("password"));
        req.setAttribute("user", userService.create(userDto));
        return "jsp/user.jsp";
    }
}
