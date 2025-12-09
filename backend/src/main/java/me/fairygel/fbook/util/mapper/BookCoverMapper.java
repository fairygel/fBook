package me.fairygel.fbook.util.mapper;

import me.fairygel.fbook.dto.book.BookCoverDTO;
import me.fairygel.fbook.entity.Book;
import me.fairygel.fbook.entity.BookCover;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookCoverMapper {
    BookCoverDTO bookCoverToDTO(BookCover bookCover);

    @Mapping(target = "imageType", expression = "java(file.getContentType())")
    @Mapping(target = "cover", expression = "java(file.getBytes())")
    @Mapping(target = "bookId", ignore = true)
    BookCover coverFromMultipartFile(Book book, MultipartFile file) throws IOException;
}
