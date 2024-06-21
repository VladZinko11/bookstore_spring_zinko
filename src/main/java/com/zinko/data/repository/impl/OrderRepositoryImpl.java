package com.zinko.data.repository.impl;

import com.zinko.data.entity.Order;
import com.zinko.data.entity.OrderItem;
import com.zinko.data.entity.Status;
import com.zinko.data.entity.User;
import com.zinko.data.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepository {
    private static final String SELECT_ALL = "FROM Order WHERE deleted=false";
    private static final String SELECT_BY_USER_ID = "FROM Order WHERE deleted=false AND user=:user AND status='ISSUED' OR status= 'PROCESSED' OR status = 'DELIVERED'";
    private static final String SELECT_BASKET = "FROM Order WHERE deleted=false AND user=:user AND status='BASKET'";
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Order> findAll() {
        return manager.createQuery(SELECT_ALL, Order.class).getResultList();
    }

    @Override
    public Order save(Order entity) {
        if (entity.getId() != null) {
            manager.merge(entity);
        } else {
            manager.persist(entity);
        }
        return entity;
    }

    @Override
    public boolean delete(Long key) {
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

    @Override
    public Order getBasket(User user) {
        Order order;
        try {
            order = manager.createQuery(SELECT_BASKET, Order.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            order = new Order();
            order.setUser(user);
            order.setStatus(Status.BASKET);
            manager.persist(order);
        }
        return order;
    }
}
