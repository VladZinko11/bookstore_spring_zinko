package com.zinko.controller.commands.impl;

import com.zinko.controller.commands.Command;
import com.zinko.service.UserService;
import com.zinko.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("user_edit")
@RequiredArgsConstructor
public class UserEditCommand implements Command {

    private final UserService userService;

    @Override
    public String execute(HttpServletRequest req) {
        UserDto userDto = UserDto.builder()
                .id(Long.valueOf(req.getParameter("id")))
                .firstName(req.getParameter("firstName"))
                .lastName(req.getParameter("lastName"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();
        userService.update(userDto);
        Long id = userDto.getId();
        UserDto user = userService.findById(id);
        req.setAttribute("user", user);
        return "jsp/user.jsp";
    }
}
