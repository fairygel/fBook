package me.fairygel.fbook.service;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dao.GenreDAO;
import me.fairygel.fbook.entity.Genre;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreDAO genreDAO;

    public void create(Genre genre) {
        genreDAO.create(genre);
    }

    public Genre read(Long id) {
        return genreDAO.read(id);
    }

    public Genre update(Long id, Genre genre) {
        return genreDAO.update(id, genre);
    }

    public void delete(Long id) {
        genreDAO.delete(id);
    }

    public List<Genre> index() {
        return genreDAO.index();
    }
}
