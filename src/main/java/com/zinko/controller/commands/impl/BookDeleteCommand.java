package com.zinko.controller.commands.impl;

import com.zinko.controller.commands.Command;
import com.zinko.service.BookService;
import com.zinko.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller("book_delete")
@RequiredArgsConstructor
public class BookDeleteCommand implements Command {

    private final BookService bookService;

    @Override
    public String execute(HttpServletRequest req) {
        Long id = Long.valueOf(req.getParameter("id"));
        bookService.delete(id);
        List<BookDto> books = bookService.findAll();
        req.setAttribute("books", books);
        return "jsp/books.jsp";
    }
}
