package me.fairygel.fbook.dao;

import me.fairygel.fbook.entity.Author;
import me.fairygel.fbook.util.PropertyMerger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorDAO {
    private final List<Author> authors = new ArrayList<>();

    public void create(Author author) {
        authors.add(author);
    }

    public Author read(long id) {
        return authors.stream().filter(a -> a.getId() == id).findAny().orElse(null);
    }

    public Author update(long id, Author author) {
        Author existingAuthor = read(id);

        PropertyMerger.merge(author, existingAuthor);

        return existingAuthor;
    }

    public void delete(long id) {
        authors.removeIf(a -> a.getId() == id);
    }

    public List<Author> index() {
        return authors;
    }
}
