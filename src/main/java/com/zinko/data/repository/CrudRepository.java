package com.zinko.data.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository <T, K> {

    Optional<T> findById(K key);

    List<Optional<T>> findAll();

    void save(T entity);

    boolean delete(K key);
}
