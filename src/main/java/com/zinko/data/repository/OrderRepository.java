package com.zinko.data.repository;

import com.zinko.data.entity.Order;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long>{
    List<Order> findByUserId(Long key);

}
