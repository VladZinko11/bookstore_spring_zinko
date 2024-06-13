package com.zinko.service.impl;

import com.zinko.data.entity.User;
import com.zinko.data.repository.UserRepository;
import com.zinko.exception.EmptyRepositoryException;
import com.zinko.exception.FailedLoginException;
import com.zinko.exception.InvalidIndexException;
import com.zinko.exception.OccupiedElementException;
import com.zinko.service.UserService;
import com.zinko.service.dto.UserDto;
import com.zinko.service.serviceMapper.ServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .map(serviceMapper::toDto)
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
        return serviceMapper.toDto(user);
    }

    @Transactional
    @Override
    public void create(UserDto userDto) {
        log.debug("UserService method create call {}", userDto);
        emailValidate(userDto);
        userRepository.save(serviceMapper.toUser(userDto));
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
    public void update(UserDto userDto) {
        log.debug("UserService method update call {}", userDto);
        emailValidate(userDto);
        userRepository.save(serviceMapper.toUser(userDto));
    }

    @Override
    public void delete(Long id) {
        log.debug("UserService method delete call with id: {}", id);
        if (!userRepository.delete(id)) {
            throw new InvalidIndexException("User with id: " + id + " not exist");
        }
    }

    @Override
    public UserDto login(String email, String password) {
        log.debug("UserService method login call with email {} and password {}", email, password);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new FailedLoginException("Not found user with email: " + email));
        if (!user.getPassword().equals(password)) {
            throw new FailedLoginException("Wrong password");
        }
        return serviceMapper.toDto(user);
    }

    @Override
    public UserDto findByEmail(String email) {
        log.debug("UserService method findByEmail call with email {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidIndexException("Not found user with email: " + email));
        return serviceMapper.toDto(user);
    }
}
