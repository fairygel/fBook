package me.fairygel.fbook.util.mapper;

import me.fairygel.fbook.dto.book.status.BookStatusDTO;
import me.fairygel.fbook.dto.book.status.BookStatusIndexViewDTO;
import me.fairygel.fbook.entity.BookStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookStatusMapper {
    @Mapping(target = "name", source = "status")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    BookStatus bookStatusDtoToBookStatus(BookStatusDTO bookStatusDTO);

    @Mapping(target = "status", source = "name")
    BookStatusDTO bookStatusToBookStatusDto(BookStatus bookStatus);

    @Mapping(target = "status", source = "name")
    BookStatusIndexViewDTO bookStatusToBookStatusIndexDto(BookStatus bookStatus);

    Set<BookStatusIndexViewDTO> bookStatusesToIndex(Set<BookStatus> bookStatuses);
}
