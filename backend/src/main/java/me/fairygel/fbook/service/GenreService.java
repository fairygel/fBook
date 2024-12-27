package me.fairygel.fbook.service;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.genre.GenreDTO;
import me.fairygel.fbook.dto.genre.GenreIndexViewDTO;
import me.fairygel.fbook.entity.Genre;
import me.fairygel.fbook.mapper.GenreMapper;
import me.fairygel.fbook.repository.GenreCrudRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreCrudRepository genreCrudRepository;
    private final GenreMapper mapper;

    public void create(GenreDTO genreDTO) {
        Genre genre = mapper.genreDtoToGenre(genreDTO);
        genreCrudRepository.save(genre);
    }

    public GenreDTO read(Long id) {
        Genre genre = genreCrudRepository
                .findById(id).orElse(
                        genreCrudRepository.findById(0L)
                                .orElseThrow(IllegalStateException::new)
                );
        return mapper.genreToGenreDto(genre);
    }

    public GenreDTO update(Long id, GenreDTO genreDTO) {
        Genre genre = mapper.genreDtoToGenre(genreDTO);

        Genre updatedGenre = genreCrudRepository.updateById(id, genre).orElseThrow(IllegalAccessError::new);

        return mapper.genreToGenreDto(updatedGenre);
    }

    public void delete(Long id) {
        genreCrudRepository.deleteById(id);
    }

    public Set<GenreIndexViewDTO> index() {
        Set<Genre> genres = new HashSet<>();
        genreCrudRepository.findAll().forEach(genres::add);

        return mapper.genresToIndex(genres);
    }
}
