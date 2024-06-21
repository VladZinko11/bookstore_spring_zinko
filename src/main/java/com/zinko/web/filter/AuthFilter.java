package com.zinko.web.filter;

import com.zinko.service.dto.userDto.UserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AuthFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        UserDto user = (UserDto) req.getSession().getAttribute("user");
        if (user == null) {
            List<String> supportedUri = new ArrayList<>(List.of("/", "/books/all", "/login", "/create"));
            for (String uri :
                    supportedUri) {
                if (req.getRequestURI().equals(uri)) {
                    log.debug("allowed URI " + req.getRequestURI());
                    req.getRequestDispatcher(req.getRequestURI()).forward(req, res);
                    return;
                }
            }
            log.debug("call forward to /login");
            res.sendRedirect("/login");
        } else {
            super.doFilter(req, res, chain);
        }
    }
}
