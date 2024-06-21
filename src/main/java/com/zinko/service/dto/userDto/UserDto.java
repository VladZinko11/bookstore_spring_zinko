package com.zinko.service.dto.userDto;

import com.zinko.data.entity.Role;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
