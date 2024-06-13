package com.zinko.controller.commands.impl;

import com.zinko.controller.commands.Command;
import com.zinko.service.UserService;
import com.zinko.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("user_create")
@RequiredArgsConstructor
public class UserCreateCommand implements Command {

    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        UserDto userDto = UserDto.builder()
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();
        userService.create(userDto);
        String email = userDto.getEmail();
        UserDto user = userService.findByEmail(email);
        req.setAttribute("user", user);
        return "jsp/user.jsp";
    }
}
