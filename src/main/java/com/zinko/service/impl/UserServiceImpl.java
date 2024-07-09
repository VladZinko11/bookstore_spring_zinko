package com.zinko.service.impl;

import com.zinko.data.entity.User;
import com.zinko.data.repository.UserRepository;
import com.zinko.service.UserService;
import com.zinko.service.dto.userDto.UserCreateDto;
import com.zinko.service.dto.userDto.UserDto;
import com.zinko.service.exception.EmptyRepositoryException;
import com.zinko.service.exception.FailedLoginException;
import com.zinko.service.exception.InvalidIndexException;
import com.zinko.service.exception.OccupiedElementException;
import com.zinko.service.serviceMapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public List<UserDto> findAll() {
        log.debug("UserService method findAll call");
        List<UserDto> list = userRepository.findAll().stream()
                .map(serviceMapper::toUserDtoFromUser)
                .sorted(Comparator.comparingLong(UserDto::getId))
                .toList();
        if (list.isEmpty()) {
            throw new EmptyRepositoryException("No registered users");
        }
        return list;
    }

    @Override
    public UserDto findById(Long id) {
        log.debug("UserService method findById call with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new InvalidIndexException("User with id: " + id + " not exist"));
        return serviceMapper.toUserDtoFromUser(user);
    }

    @Transactional
    @Override
    public UserDto create(UserCreateDto userCreateDto) {
        log.debug("UserService method create call {}", userCreateDto);
        UserDto userDto = serviceMapper.toUserDtoFromUserCreateDto(userCreateDto);
        emailValidate(userDto);
        User user = userRepository.save(serviceMapper.toUserFromUserDto(userDto));
        return serviceMapper.toUserDtoFromUser(user);
    }

    private void emailValidate(UserDto userDto) {
        String email = userDto.getEmail();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!Objects.equals(user.getId(), userDto.getId())) {
                throw new OccupiedElementException("User with email: " + userDto.getEmail() + " is exist");
            }
        }
    }

    @Transactional
    @Override
    public UserDto update(UserDto userDto) {
        log.debug("UserService method update call {}", userDto);
        emailValidate(userDto);
        userRepository.save(serviceMapper.toUserFromUserDto(userDto));
        return userDto;
    }

    @Override
    public void delete(Long id) {
        log.debug("UserService method delete call with id: {}", id);
        if (!userRepository.delete(id)) {
            throw new InvalidIndexException("User with id: " + id + " not exist");
        }
    }

    @Override
    public UserDto login(UserDto userDto) {
        log.debug("UserService method login call with email {} and password {}", userDto.getEmail(), userDto.getPassword());
        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new FailedLoginException("Not found user with email: " + userDto.getEmail()));
        if (!user.getPassword().equals(userDto.getPassword())) {
            throw new FailedLoginException("Wrong password");
        }
        return serviceMapper.toUserDtoFromUser(user);
    }
}
