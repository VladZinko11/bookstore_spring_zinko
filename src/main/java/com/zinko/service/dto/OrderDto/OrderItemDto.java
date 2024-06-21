package com.zinko.service.dto.OrderDto;

import com.zinko.service.dto.bookDto.BookDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private BookDto bookDto;
    private Integer quantity;
    private BigDecimal price;
}
