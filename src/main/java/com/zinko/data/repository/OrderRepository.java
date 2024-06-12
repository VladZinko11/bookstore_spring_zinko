package com.zinko.data.repository;

import com.zinko.data.entity.Order;
import com.zinko.data.entity.User;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long>{
    List<Order> findByUser(User user);

}
