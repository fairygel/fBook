package me.fairygel.fbook.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import me.fairygel.fbook.util.PropertyMerger;

import java.util.Optional;

public class UpdateRepositoryImpl<T> implements UpdateRepository<T> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public Optional<T> updateById(Long id, T entity) {
        T existingEntity = (T) entityManager.find(entity.getClass(), id);

        if (existingEntity == null) return Optional.empty();
        PropertyMerger.merge(entity, existingEntity);

        return Optional.of(entityManager.merge(existingEntity));
    }
}
