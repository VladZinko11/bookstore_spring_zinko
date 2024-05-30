package com.zinko.data.dao.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long OrderId;
    private Long BookID;
    private Integer quantity;
    private BigDecimal price;
}
