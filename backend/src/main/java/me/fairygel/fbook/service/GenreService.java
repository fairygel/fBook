package me.fairygel.fbook.service;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.entity.Genre;
import me.fairygel.fbook.repository.GenreCrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreCrudRepository genreCrudRepository;

    public void create(Genre genre) {
        genreCrudRepository.save(genre);
    }

    public Genre read(Long id) {
        return genreCrudRepository
                .findById(id).orElse(
                        genreCrudRepository.findById(0L)
                                .orElseThrow(IllegalStateException::new)
                );
    }

    public Genre update(Long id, Genre genre) {
        return genreCrudRepository.updateById(id, genre).orElseThrow(IllegalAccessError::new);
    }

    public void delete(Long id) {
        genreCrudRepository.deleteById(id);
    }

    public List<Genre> index() {
        List<Genre> genres = new ArrayList<>();
        genreCrudRepository.findAll().forEach(genres::add);
        return genres;
    }
}
