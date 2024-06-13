package com.zinko.controller.commands.impl;

import com.zinko.controller.commands.Command;
import com.zinko.service.BookService;
import com.zinko.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
@Controller("book_create")
public class BookCreateCommand implements Command {

    private final BookService bookService;

    private static BookDto getBookDto(HttpServletRequest req) {
        BookDto bookDto = BookDto.builder()
                .author(req.getParameter("author"))
                .title(req.getParameter("title"))
                .isbn(req.getParameter("isbn"))
                .price(BigDecimal.valueOf(Long.parseLong(req.getParameter("price"))))
                .publicationDate(LocalDate.parse(req.getParameter("publication_date")))
                .build();
        return bookDto;
    }

    @Override
    public String execute(HttpServletRequest req) {
        BookDto bookDto = getBookDto(req);
        bookService.create(bookDto);
        String isbn = bookDto.getIsbn();
        BookDto book = bookService.findByIsbn(isbn);
        req.setAttribute("book", book);
        return "jsp/book.jsp";
    }
}
