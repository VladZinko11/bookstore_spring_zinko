package com.zinko.web.controller;

import com.zinko.service.UserService;
import com.zinko.service.dto.userDto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") Long id, Model model) {
        UserDto user = userService.findById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/account")
    public String getAccount(HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("user");
        Long id = userDto.getId();
        return "redirect:/users/" + id;
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        List<UserDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users/all";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        UserDto user = userService.findById(id);
        model.addAttribute("user", user);
        return "userEdit";
    }

    @PostMapping("/edit")
    private String edit(UserDto userDto) {
        UserDto user = userService.update(userDto);
        return "redirect:/users/" + user.getId();
    }

}
