package com.zinko.data.repository.impl;

import com.zinko.data.entity.Order;
import com.zinko.data.entity.OrderItem;
import com.zinko.data.repository.OrderItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class OrderItemRepositoryImpl implements OrderItemRepository {

    public static final String SELECT_ALL = "FROM OrderItem WHERE deleted=false";
    public static final String SELECT_BY_ORDER = "FROM OrderItem WHERE order=:order AND deleted=false ORDER BY id";
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<OrderItem> findById(Long key) {
        OrderItem orderItem = manager.find(OrderItem.class, key);
        if (orderItem != null && orderItem.getDeleted()) {
            return Optional.empty();
        }
        return Optional.ofNullable(orderItem);
    }

    @Override
    public List<OrderItem> findAll() {
        return manager.createQuery(SELECT_ALL, OrderItem.class).getResultList();
    }

    @Override
    public OrderItem save(OrderItem entity) {
        if (entity.getId() == null) {
            manager.persist(entity);
        } else {
            manager.merge(entity);
        }
        return entity;
    }

    @Override
    public boolean delete(Long key) {
        OrderItem orderItem = manager.find(OrderItem.class, key);
        if (orderItem == null) {
            return false;
        }
        orderItem.setDeleted(true);
        return true;
    }

    @Override
    public List<Optional<OrderItem>> findByOrder(Order order) {
        List<OrderItem> list = manager.createQuery(SELECT_BY_ORDER, OrderItem.class)
                .setParameter("order", order)
                .getResultList();
        return list.stream()
                .map(Optional::ofNullable)
                .toList();
    }
}
