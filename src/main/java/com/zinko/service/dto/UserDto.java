package com.zinko.service.dto;

import com.zinko.data.entity.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;


}
