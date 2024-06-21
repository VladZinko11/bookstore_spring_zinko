package com.zinko.web.controller;

import com.zinko.service.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleError(Exception e, Model model) {
        log.error(e.getMessage(), e);
        model.addAttribute("message", "Oops, something wrong with server");
        return "error";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleError(BookStoreException e, Model model) {
        log.error(e.getMessage(), e);
        model.addAttribute("message", "Oops, something wrong with server");
        return "error";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String handleError(EmptyRepositoryException e, Model model) {
        log.error(e.getMessage(), e);
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleError(InvalidIndexException e, Model model) {
        log.error(e.getMessage(), e);
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleError(FailedLoginException e, Model model) {
        log.error(e.getMessage(), e);
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleError(OccupiedElementException e, Model model) {
        log.error(e.getMessage(), e);
        model.addAttribute("message", e.getMessage());
        return "error";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String handleError(NonAccessException e, Model model) {
        log.error(e.getMessage(), e);
        model.addAttribute("message", e.getMessage());
        return "error";
    }

}
