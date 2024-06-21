package com.zinko.web.controller;

import com.zinko.service.exception.NonAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/non-access")
    public void exception() {
        throw new NonAccessException("You do not have access to these resources");
    }
}
