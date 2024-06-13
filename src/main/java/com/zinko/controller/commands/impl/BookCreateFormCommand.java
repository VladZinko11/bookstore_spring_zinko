package com.zinko.controller.commands.impl;

import com.zinko.controller.commands.Command;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("book_create_form")
public class BookCreateFormCommand implements Command {

    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/bookCreate.jsp";
    }
}
