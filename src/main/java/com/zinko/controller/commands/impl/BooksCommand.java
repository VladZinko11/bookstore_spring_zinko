package com.zinko.controller.commands.impl;

import com.zinko.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("books")
public class BooksCommand extends AbstractBookCommand {

    public BooksCommand(BookService bookService) {
        super(bookService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        req.setAttribute("books", bookService.findAll());
        return "jsp/books.jsp";
    }
}
