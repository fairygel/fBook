package me.fairygel.fbook.mapper;

import me.fairygel.fbook.dto.author.AuthorDTO;
import me.fairygel.fbook.dto.author.AuthorIndexViewDTO;
import me.fairygel.fbook.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {
    AuthorDTO authorToAuthorDto(Author author);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    Author authorDtoToAuthor(AuthorDTO authorDTO);

    @Mapping(target = "fullName", expression = "java(author.getLastName() + \" \" + author.getFirstName())")
    AuthorIndexViewDTO authorToAuthorIndexDto(Author author);

    Set<AuthorIndexViewDTO> authorsToIndexDto(Set<Author> authors);
}
