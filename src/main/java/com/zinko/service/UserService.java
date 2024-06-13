package com.zinko.service;

import com.zinko.service.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto findById(Long id);

    void create(UserDto userDto);

    void update(UserDto userDto);

    void delete(Long id);

    UserDto login(String email, String password);

    UserDto findByEmail(String email);
}
