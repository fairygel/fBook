package me.fairygel.fbook.service;

import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.book.BookFullViewDTO;
import me.fairygel.fbook.dto.book.CreateBookDTO;
import me.fairygel.fbook.dto.book.IndexBookViewDTO;
import me.fairygel.fbook.dto.book.UpdateBookDTO;
import me.fairygel.fbook.entity.Book;
import me.fairygel.fbook.mapper.impl.BookMapperImpl;
import me.fairygel.fbook.repository.BookCrudRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class BookService {
    private final BookMapperImpl mapper;
    private final BookCrudRepository bookRepository;

    public void create(CreateBookDTO bookDTO) {
        Book book = mapper.createBookDtoToBook(bookDTO);

        bookRepository.save(book);
    }
    public BookFullViewDTO read(long id) {
        Book book = bookRepository.findById(id).orElseThrow(IllegalAccessError::new);

        return mapper.bookToBookFullViewDto(book);
    }
    public BookFullViewDTO update(long id, UpdateBookDTO bookDTO) {
        Book book = mapper.updateBookDtoToBook(bookDTO);
        Book updatedBook = bookRepository.updateById(id, book).orElseThrow(IllegalAccessError::new);

        return mapper.bookToBookFullViewDto(updatedBook);
    }
    public void delete(long id) {
        bookRepository.deleteById(id);
    }
    public Set<IndexBookViewDTO> index() {
        Set<IndexBookViewDTO> books = new HashSet<>();

        bookRepository.findAll().forEach(book -> books.add(mapper.bookToIndexBookViewDto(book)));
        return books;
    }
}
