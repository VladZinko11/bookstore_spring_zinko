package com.zinko.data.dao.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@EqualsAndHashCode
public class OrderItem {
    private Book book;
    private Integer quantity;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    private BigDecimal price;

    public BigDecimal getPrice() {
        price = new BigDecimal(0);
        if (quantity != null) {
            price = book.getPrice().multiply(BigDecimal.valueOf(quantity));
            return price;
        }
        else return null;
    }
}
