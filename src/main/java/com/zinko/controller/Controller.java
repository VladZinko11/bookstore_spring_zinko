package com.zinko.controller;

import com.zinko.controller.commands.Command;
import com.zinko.conf.CommandFactory;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet("/controller")
public class Controller extends HttpServlet {
    private CommandFactory commandFactory;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String command = req.getParameter("command");
            Command commandInstance = commandFactory.getCommand(command);
            log.debug(commandInstance.toString());
            String page = commandInstance.execute(req);
            log.debug(page);
            req.getRequestDispatcher(page).forward(req, resp);
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("jsp/exception.jsp").forward(req, resp);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        commandFactory = CommandFactory.getInstance();
    }

    @Override
    public void destroy() {
        if (commandFactory != null) {
            commandFactory.shutdown();
        }
    }
}
