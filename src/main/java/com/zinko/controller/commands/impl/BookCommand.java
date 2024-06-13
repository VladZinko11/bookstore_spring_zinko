package com.zinko.controller.commands.impl;

import com.zinko.controller.commands.Command;
import com.zinko.service.BookService;
import com.zinko.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller("book")
@RequiredArgsConstructor
public class BookCommand implements Command {

    private final BookService bookService;

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.valueOf(req.getParameter("id"));
        BookDto bookDto = bookService.findById(id);
        req.setAttribute("book", bookDto);
        return "jsp/book.jsp";
    }
}
