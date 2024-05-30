package com.zinko.controller.commands.impl;

import com.zinko.controller.commands.Command;
import com.zinko.service.OrderService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractOrderCommand implements Command {
    protected final OrderService orderService;
}
