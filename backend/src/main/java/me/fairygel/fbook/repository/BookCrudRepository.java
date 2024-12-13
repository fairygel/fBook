package me.fairygel.fbook.repository;

import me.fairygel.fbook.entity.Book;
import me.fairygel.fbook.repository.custom.UpdateRepository;
import org.springframework.data.repository.CrudRepository;

public interface BookCrudRepository extends CrudRepository<Book, Long>, UpdateRepository<Book> {
}
