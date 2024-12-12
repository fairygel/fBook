package me.fairygel.fbook.dao;

import me.fairygel.fbook.entity.BookType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookTypeReadOnlyDAO {
    private final List<BookType> bookTypes = new ArrayList<>();

    public BookType read(long id) {
        return bookTypes.stream().filter(t -> t.getId() == id).findAny().orElse(null);
    }

    public List<BookType> index() {
        return bookTypes;
    }
}
