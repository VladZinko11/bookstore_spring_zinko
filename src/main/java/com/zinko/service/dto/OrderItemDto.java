package com.zinko.service.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemDto {
    private BookDto bookDto;
    private Integer quantity;
    private BigDecimal price;
}
