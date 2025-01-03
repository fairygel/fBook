package me.fairygel.fbook.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import me.fairygel.fbook.dto.book.BookFullViewDTO;
import me.fairygel.fbook.dto.book.CreateBookDTO;
import me.fairygel.fbook.dto.book.IndexBookViewDTO;
import me.fairygel.fbook.dto.book.UpdateBookDTO;
import me.fairygel.fbook.entity.Book;
import me.fairygel.fbook.util.BookAutomation;
import me.fairygel.fbook.util.mapper.impl.BookMapperImpl;
import me.fairygel.fbook.repository.BookCrudRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class BookService {
    private final BookMapperImpl mapper;
    private final BookAutomation bookAutomation;
    private final BookCrudRepository bookRepository;

    public void create(CreateBookDTO bookDTO) {
        Book book = mapper.createBookDtoToBook(bookDTO);

        bookRepository.save(book);
    }
    public BookFullViewDTO read(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No book with id = " + id));

        return mapper.bookToBookFullViewDto(book);
    }
    public BookFullViewDTO update(long id, UpdateBookDTO bookDTO) {
        Book book = mapper.updateBookDtoToBook(bookDTO);

        bookAutomation.automate(book);

        Book updatedBook = bookRepository.updateById(id, book)
                .orElseThrow(() -> new EntityNotFoundException("No book with id = " + id));

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
