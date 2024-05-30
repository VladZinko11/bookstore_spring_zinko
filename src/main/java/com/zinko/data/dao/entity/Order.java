package com.zinko.data.dao.entity;

import com.zinko.data.dao.entity.enums.Status;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode
@ToString
public class Order {
    private Long id;
    private List<OrderItem> orderItems;
    private User user;
    private BigDecimal cost;
    private Status status = Status.ISSUED;

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return this.id;
    }

    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public User getUser() {
        return this.user;
    }

    public BigDecimal getCost() {
        cost = new BigDecimal(0);
        orderItems.stream()
                .map(OrderItem::getPrice)
                .filter(Objects::nonNull)
                .forEach(price->cost = cost.add(price));
        return cost;
    }

    public Status getStatus() {
        return this.status;
    }
}
