package me.fairygel.fbook.dao;

import me.fairygel.fbook.entity.BookStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookStatusReadOnlyDAO {
    private final List<BookStatus> bookStatuses = new ArrayList<>();


    public BookStatus read(long id) {
        return bookStatuses.stream().filter(s -> s.getId() == id).findAny().orElse(null);
    }

    public List<BookStatus> index() {
        return bookStatuses;
    }
}
