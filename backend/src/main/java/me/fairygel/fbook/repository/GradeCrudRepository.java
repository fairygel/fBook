package me.fairygel.fbook.repository;

import me.fairygel.fbook.entity.Grade;
import me.fairygel.fbook.repository.custom.UpdateRepository;
import org.springframework.data.repository.CrudRepository;

public interface GradeCrudRepository extends CrudRepository<Grade, Long>, UpdateRepository<Grade> {
}
