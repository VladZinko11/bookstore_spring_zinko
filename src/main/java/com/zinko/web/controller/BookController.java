package com.zinko.web.controller;

import com.zinko.service.BookService;
import com.zinko.service.dto.bookDto.BookCreateDto;
import com.zinko.service.dto.bookDto.BookDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller()
@RequiredArgsConstructor
@RequestMapping("/books")
@Slf4j
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        BookDto bookDto = bookService.findById(id);
        model.addAttribute("book", bookDto);
        return "book";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/create")
    public String createForm() {
        return "bookCreate";
    }

    @PostMapping("/create")
    public String create(BookCreateDto bookDto) {
        BookDto book = bookService.create(bookDto);
        return "redirect:/books/" + book.getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        bookService.delete(id);
        return "redirect:/books/all";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        BookDto bookDto = bookService.findById(id);
        model.addAttribute("book", bookDto);
        return "bookEdit";
    }

    @PostMapping("/edit")
    public String edit(BookDto bookDto) {
        BookDto book = bookService.update(bookDto);
        return "redirect:/books/" + book.getId();
    }
}
