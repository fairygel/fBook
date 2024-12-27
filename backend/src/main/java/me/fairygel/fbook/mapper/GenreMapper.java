package me.fairygel.fbook.mapper;

import me.fairygel.fbook.dto.genre.GenreDTO;
import me.fairygel.fbook.dto.genre.GenreIndexViewDTO;
import me.fairygel.fbook.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenreMapper {
    @Mapping(target = "name", source = "genre")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    Genre genreDtoToGenre(GenreDTO genreDTO);

    @Mapping(target = "genre", source = "name")
    GenreDTO genreToGenreDto(Genre genre);

    @Mapping(target = "genre", source = "name")
    GenreIndexViewDTO genreToGenreIndex(Genre genre);

    Set<GenreIndexViewDTO> genresToIndex(Set<Genre> genre);
}
