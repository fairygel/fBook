package me.fairygel.fbook.repository.custom;

import java.util.Optional;

// custom repository, that updates an entity by id.
public interface UpdateRepository<T, U> {
    Optional<U> updateById(T id, U entity);
}
