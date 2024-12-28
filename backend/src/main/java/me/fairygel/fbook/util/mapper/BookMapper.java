package me.fairygel.fbook.util.mapper;

import me.fairygel.fbook.dto.book.BookFullViewDTO;
import me.fairygel.fbook.dto.book.CreateBookDTO;
import me.fairygel.fbook.dto.book.IndexBookViewDTO;
import me.fairygel.fbook.dto.book.UpdateBookDTO;
import me.fairygel.fbook.entity.Book;
import org.springframework.stereotype.Component;

@Component
public interface BookMapper {
    Book createBookDtoToBook(CreateBookDTO bookDTO);
    Book updateBookDtoToBook(UpdateBookDTO bookDTO);
    BookFullViewDTO bookToBookFullViewDto(Book book);
    IndexBookViewDTO bookToIndexBookViewDto(Book book);
}
