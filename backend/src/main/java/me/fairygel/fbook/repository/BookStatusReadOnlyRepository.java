package me.fairygel.fbook.repository;

import me.fairygel.fbook.entity.BookStatus;
import org.springframework.data.repository.CrudRepository;

public interface BookStatusReadOnlyRepository extends CrudRepository<BookStatus, Short> {
}
