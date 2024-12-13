package me.fairygel.fbook.repository.custom;

import java.util.Optional;

public interface UpdateRepository<T> {
    Optional<T> updateById(Long id, T entity);
}
