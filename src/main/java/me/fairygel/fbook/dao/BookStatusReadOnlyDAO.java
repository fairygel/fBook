package me.fairygel.fbook.dao;

import me.fairygel.fbook.entity.BookStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookStatusReadOnlyDAO {
    private final List<BookStatus> bookStatuses = new ArrayList<>();

    public BookStatusReadOnlyDAO() {
        bookStatuses.add(new BookStatus(1L, "not started"));
        bookStatuses.add(new BookStatus(2L, "planned"));
        bookStatuses.add(new BookStatus(3L, "reading"));
        bookStatuses.add(new BookStatus(4L, "finished"));
        bookStatuses.add(new BookStatus(5L, "postponed"));
        bookStatuses.add(new BookStatus(6L, "abandoned"));
    }

    public BookStatus read(long id) {
        return bookStatuses.stream().filter(s -> s.getId() == id).findAny().orElse(null);
    }

    public List<BookStatus> index() {
        return bookStatuses;
    }
}
