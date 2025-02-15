package me.fairygel.fbook.repository;

import me.fairygel.fbook.entity.Author;
import me.fairygel.fbook.repository.custom.UpdateRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorCrudRepository extends CrudRepository<Author, Long>, UpdateRepository<Author> {
}
