package com.zinko.web.filter;

import com.zinko.data.entity.Role;
import com.zinko.service.dto.userDto.UserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;


@Slf4j
public class SecurityFilter extends HttpFilter {

    List<String> unsupportedUri = List.of("/users/all", "/users/delete/", "/books/create", "/books/delete/", "/books/edit/", "/books/edit", "/orders/all", "/orders/delete", "orders/user_id", "orders/create", "orders/update/");

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        UserDto user = (UserDto) req.getSession().getAttribute("user");
        if (user != null) {
            if (!(user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.MANAGER))) {
                for (String uri :
                        unsupportedUri) {
                    if (req.getRequestURI().contains(uri)) {
                        req.getRequestDispatcher("/non-access").forward(req, res);
                        return;
                    }
                }
            }
        }
        chain.doFilter(req, res);
    }
}
