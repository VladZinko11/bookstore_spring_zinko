package com.zinko.controller.commands.impl;

import com.zinko.controller.commands.Command;
import com.zinko.service.BookService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractBookCommand implements Command {
    protected final BookService bookService;
}
