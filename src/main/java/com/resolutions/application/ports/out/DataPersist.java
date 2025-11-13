package com.resolutions.application.ports.out;

import com.resolutions.model.PaginatedResponse;

import java.util.Optional;

public interface DataPersist<T> {
    Integer insert(T dto);
    void update(T dto);
    void delete(T dto);
    T get(Integer cod);
    Optional<T> findByDescription(String description);
    PaginatedResponse<T> findAll(int page, int size);
}