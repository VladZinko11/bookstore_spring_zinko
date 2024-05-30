package com.zinko.data.dao.dto;

import com.zinko.data.dao.entity.enums.Status;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private Status status=Status.ISSUED;
    private BigDecimal cost;
}
