package com.zinko.controller.commands.impl;

import com.zinko.service.BookService;
import com.zinko.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller("book_create")
public class BookCreateCommand extends AbstractBookCommand {
    public BookCreateCommand(BookService bookService) {
        super(bookService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        BookDto bookDto = BookDto.builder()
                .author(req.getParameter("author"))
                .title(req.getParameter("title"))
                .isbn(req.getParameter("isbn"))
                .price(BigDecimal.valueOf(Long.parseLong(req.getParameter("price"))))
                .publicationDate(LocalDate.parse(req.getParameter("publication_date")))
                .build();
        bookService.create(bookDto);
        req.setAttribute("book", bookService.findByIsbn(bookDto.getIsbn()));
        return "jsp/book.jsp";
    }
}
