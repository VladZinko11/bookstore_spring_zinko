package com.zinko.service.dto.order;

import com.zinko.data.entity.Status;
import com.zinko.service.dto.userDto.UserDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private List<OrderItemDto> orderItemsDto;
    private UserDto userDto;
    private BigDecimal cost;
    private Status status;
}
