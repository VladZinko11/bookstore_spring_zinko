package com.zinko.controller.commands.impl;

import com.zinko.controller.commands.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("user_create_form")
public class UserCreateFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/userCreate.jsp";
    }
}
