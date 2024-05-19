package com.zinko.data.dao.entity;

import com.zinko.data.dao.entity.enums.Role;
import lombok.Data;

@Data
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
