package me.fairygel.fbook.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.GenreDTO;
import me.fairygel.fbook.entity.Genre;
import me.fairygel.fbook.repository.GenreCrudRepository;
import me.fairygel.fbook.util.mapper.GenreMapperImpl;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreCrudRepository genreCrudRepository;
    private final GenreMapperImpl mapper;

    private static final String NO_GENRE = "No genre with ID = ";

    public GenreDTO create(GenreDTO genreDTO) {
        Genre genre = mapper.genreDtoToGenre(genreDTO);
        Genre savedGenre = genreCrudRepository.save(genre);
        return mapper.genreToGenreDto(savedGenre);
    }

    public GenreDTO read(Long id) {
        if (id == 0L) throw new EntityNotFoundException(NO_GENRE + id);

        Genre genre = genreCrudRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NO_GENRE + id));
        return mapper.genreToGenreDto(genre);
    }

    public GenreDTO update(Long id, GenreDTO genreDTO) {
        if (id == 0L) throw new EntityNotFoundException(NO_GENRE + id);

        Genre genre = mapper.genreDtoToGenre(genreDTO);

        Genre updatedGenre = genreCrudRepository.updateById(id, genre)
                .orElseThrow(() -> new EntityNotFoundException(NO_GENRE + id));

        return mapper.genreToGenreDto(updatedGenre);
    }

    public void delete(Long id) {
        if (id == 0L) throw new EntityNotFoundException(NO_GENRE + id);

        genreCrudRepository.deleteById(id);
    }

    public Set<GenreDTO> index() {
        Set<Genre> genres = new HashSet<>();
        genreCrudRepository.findAll().forEach(g -> {if (g.getId() != 0L) genres.add(g);});

        return mapper.genresToIndex(genres);
    }
}
