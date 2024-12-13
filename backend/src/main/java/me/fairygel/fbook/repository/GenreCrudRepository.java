package me.fairygel.fbook.repository;

import me.fairygel.fbook.entity.Genre;
import me.fairygel.fbook.repository.custom.UpdateRepository;
import org.springframework.data.repository.CrudRepository;

public interface GenreCrudRepository extends CrudRepository<Genre, Long>, UpdateRepository<Genre> {
}
