package com.zinko.data.repository.impl;

import com.zinko.data.entity.User;
import com.zinko.data.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    private static final String SELECT_BY_EMAIL = "FROM User WHERE email=:email AND deleted=false";
    private static final String SELECT_ALL = "FROM User WHERE deleted=false";

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void save(User entity) {
        if (entity.getId() != null) {
            manager.merge(entity);
        } else {
            manager.persist(entity);
        }
    }

    @Override
    public Optional<User> findById(Long key) {
        User user = manager.find(User.class, key);
        if (user.getDeleted()) {
            return Optional.empty();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        return manager.createQuery(SELECT_ALL, User.class).getResultList();
    }

    @Override
    public boolean delete(Long key) {
        User user = manager.find(User.class, key);
        if(user==null) {
            return false;
        }
        user.setDeleted(true);
        return true;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(manager.createQuery(SELECT_BY_EMAIL, User.class)
                .setParameter("email", email).getSingleResult());
    }
}
