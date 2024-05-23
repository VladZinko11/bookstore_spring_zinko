package com.zinko.service.dto;

import com.zinko.data.dao.entity.enums.Role;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role = Role.CUSTOMER;

}
