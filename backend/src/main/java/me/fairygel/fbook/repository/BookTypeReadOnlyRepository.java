package me.fairygel.fbook.repository;

import me.fairygel.fbook.entity.BookType;
import org.springframework.data.repository.CrudRepository;

public interface BookTypeReadOnlyRepository extends CrudRepository<BookType, Short> {
}
