package com.zinko.controller.commands.impl;

import com.zinko.controller.commands.Command;
import com.zinko.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractUserCommand implements Command {
    protected final UserService userService;

}
