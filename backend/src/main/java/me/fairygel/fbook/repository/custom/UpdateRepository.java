package me.fairygel.fbook.repository.custom;

import java.util.Optional;

// custom repository, that updates an entity by id.
public interface UpdateRepository<T> {
    Optional<T> updateById(Long id, T entity);
}
