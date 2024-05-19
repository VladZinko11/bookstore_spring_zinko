package com.zinko.controller.commands.impl;

import com.zinko.service.BookService;
import jakarta.servlet.http.HttpServletRequest;

public class BookCreateFormCommand extends AbstractBookCommand{
    public BookCreateFormCommand(BookService bookService) {
        super(bookService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/bookCreate.jsp";
    }
}
