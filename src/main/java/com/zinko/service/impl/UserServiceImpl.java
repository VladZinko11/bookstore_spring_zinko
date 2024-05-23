package com.zinko.service.impl;

import com.zinko.data.dao.UserDao;
import com.zinko.data.dao.entity.User;
import com.zinko.exception.EmptyRepositoryException;
import com.zinko.exception.FailedLoginException;
import com.zinko.exception.InvalidIndexException;
import com.zinko.exception.OccupiedElementException;
import com.zinko.service.UserService;
import com.zinko.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;


    private UserDto toDto(User user) {
        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());
            userDto.setRole(user.getRole());
            userDto.setPassword(user.getPassword());
            return userDto;
        } else return null;
    }

    private User toUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        return user;
    }

    public List<UserDto> findAll() {
        log.debug("UserService method findAll call");
        List<UserDto> list = userDao.findAll().stream().map(this::toDto).toList();
        if (list.isEmpty()) throw new EmptyRepositoryException("No registered users");
        return list;
    }

    @Override
    public UserDto findById(Long id) {
        log.debug("UserService method findById call with id: {}", id);
        User user = userDao.findById(id);
        if (user != null)
            return toDto(user);
        else throw new InvalidIndexException("User with id: " + id + " not exist");
    }

    @Override
    public UserDto create(UserDto userDto) {
        log.debug("UserService method create call {}", userDto);
        if ((userDao.findByEmail(userDto.getEmail())) != null)
            throw new OccupiedElementException("User with email " + userDto.getEmail() + " already exist");
        else return toDto(userDao.create(toUser(userDto)));
    }

    @Override
    public UserDto update(UserDto userDto) {
        log.debug("UserService method update call {}", userDto);
        User userBefore = toUser(findById(userDto.getId()));
        if (userDto.getFirstName() == null)
            userDto.setFirstName(userBefore.getFirstName());
        if (userDto.getLastName() == null)
            userDto.setLastName(userBefore.getLastName());
        if (userDto.getEmail() == null)
            userDto.setEmail(userBefore.getEmail());
        if (userDto.getPassword() == null)
            userDto.setPassword(userBefore.getPassword());
        if (userDto.getRole() == null)
            userDto.setRole(userBefore.getRole());
        User user;
        if ((user=userDao.findByEmail(userDto.getEmail()))!=null && !userBefore.equals(user))
            throw new OccupiedElementException("User with email " + userDto.getEmail() + " is exist");
        else {
            return toDto(userDao.update(toUser(userDto)));
        }
    }

    public void delete(Long id) {
        log.debug("UserService method delete call with id: {}", id);
        userDao.delete(toUser(findById(id)));
    }

    @Override
    public UserDto login(String email, String password) {
        log.debug("UserService method login call with email {} and password {}", email, password);
        User user = userDao.findByEmail(email);
        if (user == null) throw new FailedLoginException("Not found user with email: " + email);
        if (!user.getPassword().equals(password)) throw new FailedLoginException("Wrong password");
        else return toDto(user);
    }
}
