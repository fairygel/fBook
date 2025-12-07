package me.fairygel.fbook.util.mapper;

import me.fairygel.fbook.dto.book.BookFullViewDTO;
import me.fairygel.fbook.dto.book.IndexBookViewDTO;
import me.fairygel.fbook.dto.book.BookDTO;
import me.fairygel.fbook.entity.Book;
import org.springframework.stereotype.Component;

@Component
public interface BookMapper {
    Book bookDtoToBook (BookDTO bookDTO, Boolean isCreating);
    BookFullViewDTO bookToBookFullViewDto(Book book);
    IndexBookViewDTO bookToIndexBookViewDto(Book book);
}
