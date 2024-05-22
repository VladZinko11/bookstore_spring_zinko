package com.zinko.controller.commands.impl;

import com.zinko.service.BookService;
import com.zinko.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
@Controller("book_create")
public class BookCreateCommand extends AbstractBookCommand {
    public BookCreateCommand(BookService bookService) {
        super(bookService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        BookDto bookDto = new BookDto();
        bookDto.setAuthor(req.getParameter("author"));
        bookDto.setTitle(req.getParameter("title"));
        bookDto.setIsbn(req.getParameter("isbn"));
        bookDto.setPublicationDate(LocalDate.parse(req.getParameter("publication_date")));
        req.setAttribute("book", bookService.create(bookDto));
        return "jsp/book.jsp";
    }
}
