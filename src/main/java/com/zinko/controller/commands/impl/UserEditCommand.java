package com.zinko.controller.commands.impl;

import com.zinko.service.UserService;
import com.zinko.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("user_edit")
public class UserEditCommand extends AbstractUserCommand {
    public UserEditCommand(UserService userService) {
        super(userService);
    }

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
        req.setAttribute("user", userService.findById(userDto.getId()));
        return "jsp/user.jsp";
    }
}
