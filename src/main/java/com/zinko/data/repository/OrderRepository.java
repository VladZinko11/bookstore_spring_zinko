package com.zinko.data.repository;

import com.zinko.data.dao.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Optional<Order>> findAll();
    List<Optional<Order>> findByUserId(Long id);

    Optional<Order> findById(Long id);
}
