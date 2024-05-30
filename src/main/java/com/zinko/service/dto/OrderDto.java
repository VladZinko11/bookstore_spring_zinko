package com.zinko.service.dto;

import com.zinko.data.dao.entity.OrderItem;
import com.zinko.data.dao.entity.User;
import com.zinko.data.dao.entity.enums.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private List<OrderItemDto> orderItemsDto;
    private UserDto userDto;
    private BigDecimal cost;
    private Status status = Status.ISSUED;
}
