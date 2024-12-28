package me.fairygel.fbook.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import me.fairygel.fbook.util.PropertyMerger;

import java.util.Optional;

public class UpdateRepositoryImpl<T> implements UpdateRepository<T> {
    @PersistenceContext
    private EntityManager entityManager;

    /*
     * Method that updates entity in repository.
     * at first, we will find entity in database.
     * if it not exists, we will send and do nothing
     * if it exists, we will merge all fields from our entity, to existing and then save it
     */
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
