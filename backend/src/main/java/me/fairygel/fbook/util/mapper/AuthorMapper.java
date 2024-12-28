package me.fairygel.fbook.util.mapper;

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

    // if lastname is null, then we will replace it with blank line
    // else, we will place it with the space at the end
    @Mapping(target = "fullName", expression =
            "java((author.getLastName() == null?\"\":author.getLastName() + \" \").concat(author.getFirstName()))")
    AuthorIndexViewDTO authorToAuthorIndexDto(Author author);

    Set<AuthorIndexViewDTO> authorsToIndexDto(Set<Author> authors);
}
