package com.zinko.controller.commands.impl;

import com.zinko.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("book_delete")
public class BookDeleteCommand extends AbstractBookCommand{
    public BookDeleteCommand(BookService bookService) {
        super(bookService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        bookService.delete(Long.valueOf(req.getParameter("id")));
        req.setAttribute("books", bookService.findAll());
        return "jsp/books.jsp";
    }
}
