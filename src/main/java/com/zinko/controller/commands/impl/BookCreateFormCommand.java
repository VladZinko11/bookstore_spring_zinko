package com.zinko.controller.commands.impl;

import com.zinko.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("book_create_form")
public class BookCreateFormCommand extends AbstractBookCommand{
    public BookCreateFormCommand(BookService bookService) {
        super(bookService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/bookCreate.jsp";
    }
}
