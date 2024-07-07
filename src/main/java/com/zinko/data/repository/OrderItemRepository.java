package com.zinko.data.repository;

import com.zinko.data.entity.Order;
import com.zinko.data.entity.OrderItem;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);
}
