package com.zinko.service.dto;

import com.zinko.data.entity.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class OrderDto {
    private Long id;
    private List<OrderItemDto> orderItemsDto;
    private UserDto userDto;
    private BigDecimal cost;
    private Status status;
}
