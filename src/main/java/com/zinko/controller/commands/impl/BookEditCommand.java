package com.zinko.controller.commands.impl;

import com.zinko.controller.commands.Command;
import com.zinko.service.BookService;
import com.zinko.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller("book_edit")
@RequiredArgsConstructor
public class BookEditCommand implements Command {

    private final BookService bookService;

    @Override
    public String execute(HttpServletRequest req) {
        BookDto bookDto = BookDto.builder()
                .id(Long.valueOf(req.getParameter("id")))
                .author(req.getParameter("author"))
                .title(req.getParameter("title"))
                .isbn(req.getParameter("isbn"))
                .price(BigDecimal.valueOf(Long.parseLong(req.getParameter("price")))).
                publicationDate(LocalDate.parse(req.getParameter("publication_date")))
                .build();
        bookService.update(bookDto);
        Long id = bookDto.getId();
        BookDto book = bookService.findById(id);
        req.setAttribute("book", book);
        return "jsp/book.jsp";
    }
}
