package com.zinko.controller.commands.impl;

import com.zinko.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("book")
public class BookCommand extends AbstractBookCommand {

    public BookCommand(BookService bookService) {
        super(bookService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        req.setAttribute("book", bookService.findById(Long.valueOf(req.getParameter("id"))));
        return "jsp/book.jsp";
    }
}
