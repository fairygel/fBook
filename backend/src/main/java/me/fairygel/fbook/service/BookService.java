package me.fairygel.fbook.service;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dao.BookDAO;
import me.fairygel.fbook.dto.book.BookFullViewDTO;
import me.fairygel.fbook.dto.book.CreateBookDTO;
import me.fairygel.fbook.dto.book.IndexBookViewDTO;
import me.fairygel.fbook.dto.book.UpdateBookDTO;
import me.fairygel.fbook.entity.Book;
import me.fairygel.fbook.mapper.BookMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private final BookMapper mapper;
    private final BookDAO dao;

    public void create(CreateBookDTO bookDTO) {
        Book book = mapper.createBookDtoToBook(bookDTO);
        dao.create(book);
    }
    public BookFullViewDTO read(long id) {
        Book book = dao.read(id);
        if (book == null) return null;
        return mapper.bookToBookFullViewDto(book);
    }
    public BookFullViewDTO update(long id, UpdateBookDTO bookDTO) {
        Book book = mapper.updateBookDtoToBook(bookDTO);
        Book updatedBook = dao.update(id, book);
        return mapper.bookToBookFullViewDto(updatedBook);
    }
    public void delete(long id) {
        dao.delete(id);
    }
    public List<IndexBookViewDTO> index() {
        List<Book> books = dao.index();
        return books.stream().map(mapper::bookToIndexBookViewDto).toList();
    }
}
