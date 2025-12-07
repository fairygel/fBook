package me.fairygel.fbook.util.mapper;

import me.fairygel.fbook.dto.BookTypeDTO;
import me.fairygel.fbook.entity.BookType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookTypeMapper {
    @Mapping(target = "name", source = "type")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    BookType bookTypeDtoToBookType(BookTypeDTO bookTypeDTO);

    @Mapping(target = "type", source = "name")
    BookTypeDTO bookTypeToBookTypeDto(BookType bookType);

    Set<BookTypeDTO> bookTypesToIndex(Set<BookType> bookTypes);
}
