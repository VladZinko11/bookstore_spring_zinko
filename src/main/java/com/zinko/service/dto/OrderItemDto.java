package com.zinko.service.dto;

import com.zinko.data.dao.entity.Book;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private BookDto bookDto;
    private Integer quantity;
    private BigDecimal price;
}
