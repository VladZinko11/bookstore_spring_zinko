package com.zinko.data.repository.impl;

import com.zinko.data.entity.Order;
import com.zinko.data.entity.User;
import com.zinko.data.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private static final String SELECT_ALL = "FROM Order WHERE deleted=false";
    private static final String SELECT_BY_USER_ID = "FROM Order WHERE deleted=false AND user=:user";
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Order> findAll() {
        return manager.createQuery(SELECT_ALL, Order.class).getResultList();
    }

    @Override
    public void save(Order entity) {
        if (entity.getId() != null) {
            manager.merge(entity);
        } else {
            manager.persist(entity);
        }
    }

    @Override
    public boolean delete(Long key) {
        Order order = manager.find(Order.class, key);
        if (order == null) {
            return false;
        }
        order.setDeleted(true);
        return true;
    }


    @Override
    public List<Order> findByUser(User user) {
        return manager.createQuery(SELECT_BY_USER_ID, Order.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public Optional<Order> findById(Long key) {
        Order order = manager.find(Order.class, key);
        if (order != null && order.getDeleted()) {
            return Optional.empty();
        }
        return Optional.ofNullable(order);

    }
}
