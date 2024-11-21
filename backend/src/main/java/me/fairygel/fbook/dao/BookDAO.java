package me.fairygel.fbook.dao;

import me.fairygel.fbook.entity.Book;
import me.fairygel.fbook.util.PropertyMerger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookDAO {
    private final List<Book> books = new ArrayList<>();

    public void create(Book book) {
        books.add(book);
    }

    public Book read(long id) {
        return books.stream().filter(b -> b.getId() == id).findAny().orElse(null);
    }

    public Book update(long id, Book book) {
        Book existingBook = read(id);

        PropertyMerger.merge(book, existingBook);

        return existingBook;
    }

    public void delete(long id) {
        books.removeIf(b -> b.getId() == id);
    }

    public List<Book> index() {
        return books;
    }
}
