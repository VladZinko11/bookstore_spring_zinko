package com.zinko.data.repository;

import com.zinko.data.dao.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> create(User user);

    Optional<User> findById(Long id);

    List<Optional<User>> findAll();

    Optional<User> update(User user);

    boolean delete(User user);

    Optional<User> findByEmail(String email);

    List<Optional<User>> findByLastName(String lastName);

    Long countAll();
}
