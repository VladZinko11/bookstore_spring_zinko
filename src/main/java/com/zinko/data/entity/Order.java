package com.zinko.data.entity;

import com.zinko.data.entity.enums.Status;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "orders")
@EqualsAndHashCode
@ToString
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private BigDecimal cost;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.ISSUED;

    @Column(name = "deleted")
    private Boolean deleted = false;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

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
