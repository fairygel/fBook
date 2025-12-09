package me.fairygel.fbook.repository.custom;

import java.util.List;
import java.util.Optional;

// custom repository that updates an entity by id.
public interface UpdateRepository<U> {
    Optional<U> updateById(Long id, U entity);
    Optional<U> updateById(Long id, U entity, List<String> fieldsToSkip);
}
