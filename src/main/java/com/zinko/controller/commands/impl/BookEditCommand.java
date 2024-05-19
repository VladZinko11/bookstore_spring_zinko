package com.zinko.controller.commands.impl;

import com.zinko.service.BookService;
import com.zinko.service.dto.BookDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component(value = "book_edit")
public class BookEditCommand extends AbstractBookCommand {

    public BookEditCommand(BookService bookService) {
        super(bookService);
    }

    @Override
    public String execute(HttpServletRequest req) {
        BookDto newBookDto = new BookDto();
        newBookDto.setId(Long.valueOf(req.getParameter("id")));
        newBookDto.setAuthor(req.getParameter("author"));
        newBookDto.setTitle(req.getParameter("title"));
        newBookDto.setIsbn(req.getParameter("isbn"));
        newBookDto.setPublicationDate(LocalDate.parse(req.getParameter("publication_date")));
        req.setAttribute("book", bookService.update(newBookDto));
        return "jsp/book.jsp";
    }
}
