package com.zinko.service.dto.order;

import com.zinko.data.entity.Status;
import com.zinko.service.dto.userDto.UserDto;
import lombok.Data;

@Data
public class OrderGetAllDto {
    private Long id;
    private UserDto userDto;
    private Status status;
}
