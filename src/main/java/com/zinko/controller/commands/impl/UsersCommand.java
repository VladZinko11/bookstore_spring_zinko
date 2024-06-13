package com.zinko.controller.commands.impl;

import com.zinko.controller.commands.Command;
import com.zinko.service.UserService;
import com.zinko.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller("users")
@RequiredArgsConstructor
public class UsersCommand implements Command {

    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        List<UserDto> users = userService.findAll();
        req.setAttribute("users", users);
        return "jsp/users.jsp";
    }
}
