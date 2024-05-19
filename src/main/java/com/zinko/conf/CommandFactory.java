package com.zinko.conf;

import com.zinko.AppContext;
import com.zinko.controller.commands.Command;
import com.zinko.controller.commands.impl.*;
import com.zinko.data.dao.BookDao;
import com.zinko.data.dao.UserDao;
import com.zinko.data.dao.connection.MyConnectionManager;
import com.zinko.data.dao.impl.BookDaoImpl;
import com.zinko.data.dao.impl.UserDaoImpl;
import com.zinko.service.BookService;
import com.zinko.service.UserService;
import com.zinko.service.impl.BookServiceImpl;
import com.zinko.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CommandFactory {

    private static final CommandFactory INSTANCE = new CommandFactory();
    private static final String DB_URL_KEY = "db.url";
    private static final String DB_PASSWORD_KEY = "db.password";
    private static final String DB_USER_KEY = "db.user";
    private static final String DB_DRIVER_KEY = "db.driver";
    private static final String DB_POOL_SIZE_KEY = "db.poolSize";
    private final List<Closeable> resources;

    private final Map<String, Command> commands;

    private CommandFactory() {
        resources = new ArrayList<>();
        commands = new HashMap<>();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppContext.class);
        resources.add(context);
        MyConnectionManager myConnectionManager = context.getBean(MyConnectionManager.class);
        resources.add(myConnectionManager);

        BookDao bookDao = new BookDaoImpl(myConnectionManager);
        UserDao userDao = new UserDaoImpl(myConnectionManager);

        BookService bookService = new BookServiceImpl(bookDao);
        UserService userService = new UserServiceImpl(userDao);

        commands.put("books", new BooksCommand(bookService));
        commands.put("users", new UsersCommand(userService));
        commands.put("book", new BookCommand(bookService));
        commands.put("user", new UserCommand(userService));
        commands.put("book_edit_form", new BookEditFormCommand(bookService));
        commands.put("book_delete", new BookDeleteCommand(bookService));
        commands.put("book_edit", new BookEditCommand(bookService));
        commands.put("book_create", new BookCreateCommand(bookService));
        commands.put("book_create_form", new BookCreateFormCommand(bookService));
        commands.put("user_edit_form", new UserEditFormCommand(userService));
        commands.put("user_delete", new UserDeleteCommand(userService));
        commands.put("user_create_form", new UserCreateFormCommand(userService));
        commands.put("user_edit", new UserEditCommand(userService));
        commands.put("user_create", new UserCreateCommand(userService));
    }

    public static CommandFactory getInstance() {
        return INSTANCE;
    }

    public Command getCommand(String command) {
        Command commandInstance = commands.get(command);
        if (commandInstance == null) {
            commandInstance = commands.get("error");
        }
        return commandInstance;
    }

    public void shutdown() {
        resources.forEach(resources -> {
            try {
                resources.close();
            } catch (IOException e) {
                log.error("Nothing to close {}", resources);
            }
        });
    }
}
