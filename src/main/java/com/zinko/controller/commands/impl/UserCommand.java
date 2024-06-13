package com.zinko.controller.commands.impl;

import com.zinko.controller.commands.Command;
import com.zinko.service.UserService;
import com.zinko.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("user")
@RequiredArgsConstructor
public class UserCommand implements Command {

    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.valueOf(req.getParameter("id"));
        UserDto user = userService.findById(id);
        req.setAttribute("user", user);
        return "jsp/user.jsp";
    }
}
