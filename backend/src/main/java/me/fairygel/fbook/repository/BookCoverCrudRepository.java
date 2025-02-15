package me.fairygel.fbook.repository;

import me.fairygel.fbook.entity.Book;
import me.fairygel.fbook.entity.BookCover;
import me.fairygel.fbook.repository.custom.UpdateRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookCoverCrudRepository extends CrudRepository<BookCover, Book>, UpdateRepository<Long, BookCover> {

    @Query("SELECT c FROM BookCover c WHERE c.bookId = :bookId")
    Optional<BookCover> findByBookId(Long bookId);

    @Modifying
    @Query("DELETE FROM BookCover c WHERE c.bookId = :bookId")
    void deleteByBookId(Long bookId);
}
