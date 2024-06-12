package com.zinko.controller.commands.impl;

import com.zinko.service.UserService;
import com.zinko.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("user_create")
public class UserCreateCommand extends AbstractUserCommand {
    public UserCreateCommand(UserService userService) {
        super(userService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        UserDto userDto = UserDto.builder()
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();
        userService.create(userDto);
        req.setAttribute("user", userService.findByEmail(userDto.getEmail()));
        return "jsp/user.jsp";
    }
}
