package com.zinko.web.controller;

import com.zinko.service.UserService;
import com.zinko.service.dto.userDto.UserCreateDto;
import com.zinko.service.dto.userDto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
        UserDto user = userService.login(email, password);
        session.setAttribute("user", user);
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @GetMapping("/create")
    public String createFrom() {
        return "userCreate";
    }

    @PostMapping("/create")
    public String create(UserCreateDto userDto, HttpSession session) {
        UserDto user = userService.create(userDto);
        session.setAttribute("user", user);
        return "redirect:/users/" + user.getId();
    }
}
