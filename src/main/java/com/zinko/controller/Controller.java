package com.zinko.controller;

import com.zinko.AppContext;
import com.zinko.controller.commands.Command;
import com.zinko.exception.EmptyRepositoryException;
import com.zinko.exception.FailedLoginException;
import com.zinko.exception.InvalidIndexException;
import com.zinko.exception.OccupiedElementException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

@Slf4j
@WebServlet("/controller")
public class Controller extends HttpServlet {
    private AnnotationConfigApplicationContext context;

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
            Command commandInstance = context.getBean(command, Command.class);
            log.debug(commandInstance.toString());
            String page = commandInstance.execute(req);
            log.debug(page);
            req.getRequestDispatcher(page).forward(req, resp);
        } catch (EmptyRepositoryException | FailedLoginException | OccupiedElementException e) {
            log.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("jsp/exception.jsp").forward(req, resp);
        }catch (InvalidIndexException e) {
            log.error(e.getMessage(), e);
            req.setAttribute("message", e.getMessage());
            resp.setStatus(404);
            req.getRequestDispatcher("jsp/exception.jsp").forward(req, resp);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            req.setAttribute("message", "Oops, something wrong with server");
            resp.setStatus(500);
            req.getRequestDispatcher("jsp/exception.jsp").forward(req, resp);
        }
    }

    @Override
    public void init(ServletConfig config) {
        context = new AnnotationConfigApplicationContext(AppContext.class);
        log.debug("Spring context init");
    }

    @Override
    public void destroy() {
        context.close();
        log.debug("Spring context destroy");
    }
}
