package com.zinko.service;

import com.zinko.service.dto.userDto.UserCreateDto;
import com.zinko.service.dto.userDto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto findById(Long id);

    UserDto create(UserCreateDto userDto);

    UserDto update(UserDto userDto);

    void delete(Long id);

    UserDto login(UserDto userDto);


}
