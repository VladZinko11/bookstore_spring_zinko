package com.zinko.data.repository.impl;

import com.zinko.data.entity.Order;
import com.zinko.data.entity.OrderItem;
import com.zinko.data.entity.User;
import com.zinko.data.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@Slf4j
public class OrderRepositoryImpl implements OrderRepository {
    private static final String SELECT_ALL = "FROM Order WHERE deleted=false";
    private static final String SELECT_BY_USER_ID = "FROM Order WHERE deleted=false AND user=:user";
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Order> findAll() {
        log.debug("OrderRepository method findAll call");
        return manager.createQuery(SELECT_ALL, Order.class).getResultList();
    }

    @Override
    public Order save(Order entity) {
        log.debug("OrderRepository method save call for entity: {}", entity.toString());
        if (entity.getId() != null) {
            log.debug("update entity");
            manager.merge(entity);
        } else {
            log.debug("save entity");
            manager.persist(entity);
        }
        return entity;
    }

    @Override
    public boolean delete(Long key) {
        log.debug("OrderRepository method delete call with id: {}", key);
        Order order = manager.find(Order.class, key);
        if (order == null) {
            return false;
        }
        order.setDeleted(true);
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setDeleted(true);
        }
        return true;
    }

    @Override
    public List<Order> findByUser(User user) {
        log.debug("OrderRepository method findByUser call for user: {}", user.toString());
        return manager.createQuery(SELECT_BY_USER_ID, Order.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public Optional<Order> findById(Long key) {
        log.debug("OrderRepository method findById call with id: {}", key);
        Order order = manager.find(Order.class, key);
        if (order != null && order.getDeleted()) {
            return Optional.empty();
        }
        return Optional.ofNullable(order);
    }
}
