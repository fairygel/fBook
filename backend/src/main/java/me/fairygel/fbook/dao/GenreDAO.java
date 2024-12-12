package me.fairygel.fbook.dao;

import me.fairygel.fbook.entity.Genre;
import me.fairygel.fbook.util.PropertyMerger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GenreDAO {
    private final List<Genre> genres = new ArrayList<>();

    public GenreDAO() {
        Genre genre = new Genre();

        genre.setId(0L);
        genre.setName("no genre");

        genres.add(genre);
    }

    public void create(Genre genre) {
        genres.add(genre);
    }

    public Genre read(long id) {
        return genres.stream().filter(g -> g.getId() == id).findAny().orElse(null);
    }

    public Set<Genre> readAll(Set<Long> ids) {
        Set<Genre> foundGenres = new HashSet<>();

        ids.forEach(id -> foundGenres.add(read(id)));

        return foundGenres;
    }

    public Genre update(long id, Genre genre) {
        Genre existingGenre = read(id);

        PropertyMerger.merge(genre, existingGenre);

        return existingGenre;
    }

    public void delete(long id) {
        genres.removeIf(g -> g.getId() == id);
    }

    public List<Genre> index() {
        return genres;
    }
}
