package com.zinko.controller.commands.impl;

import com.zinko.service.UserService;
import com.zinko.service.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component(value = "user_edit")
public class UserEditCommand extends AbstractUserCommand{
    public UserEditCommand(UserService userService) {
        super(userService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        UserDto userDto = new UserDto();
        userDto.setId(Long.valueOf(req.getParameter("id")));
        userDto.setFirstName(req.getParameter("firstName"));
        userDto.setLastName(req.getParameter("lastName"));
        userDto.setEmail(req.getParameter("email"));
        userDto.setPassword(req.getParameter("password"));
        req.setAttribute("user", userService.update(userDto));
        return "jsp/user.jsp";
    }
}
