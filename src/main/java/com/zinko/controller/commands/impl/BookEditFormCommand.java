package com.zinko.controller.commands.impl;

import com.zinko.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

@Controller("book_edit_form")
public class BookEditFormCommand extends AbstractBookCommand {
    public BookEditFormCommand(BookService bookService) {
        super(bookService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        req.setAttribute("book", bookService.findById(Long.valueOf(req.getParameter("id"))));
        return "jsp/bookEdit.jsp";
    }
}
