package me.fairygel.fbook.mapper;

import me.fairygel.fbook.dto.book.BookFullViewDTO;
import me.fairygel.fbook.dto.book.CreateBookDTO;
import me.fairygel.fbook.dto.book.IndexBookViewDTO;
import me.fairygel.fbook.dto.book.UpdateBookDTO;
import me.fairygel.fbook.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public Book createBookDtoToBook(CreateBookDTO bookDTO) {
    }

    public BookFullViewDTO bookToBookFullViewDto(Book book) {
    }

    public Book updateBookDtoToBook(UpdateBookDTO bookDTO) {
    }

    public IndexBookViewDTO bookToIndexBookViewDto(Book b) {
    }
}
