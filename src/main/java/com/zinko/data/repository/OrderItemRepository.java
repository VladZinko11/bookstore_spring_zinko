package com.zinko.data.repository;

import com.zinko.data.entity.Order;
import com.zinko.data.entity.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

    List<Optional<OrderItem>> findByOrder(Order order);
}
