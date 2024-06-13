package com.zinko.controller.commands.impl;

import com.zinko.controller.commands.Command;
import com.zinko.service.BookService;
import com.zinko.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller("books")
@RequiredArgsConstructor
public class BooksCommand implements Command {

    private final BookService bookService;

    @Override
    public String execute(HttpServletRequest req) {
        List<BookDto> books = bookService.findAll();
        req.setAttribute("books", books);
        return "jsp/books.jsp";
    }
}
