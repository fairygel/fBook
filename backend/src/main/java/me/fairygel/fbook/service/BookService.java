package me.fairygel.fbook.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import me.fairygel.fbook.dto.book.*;
import me.fairygel.fbook.entity.Book;
import me.fairygel.fbook.util.BookAutomation;
import me.fairygel.fbook.util.mapper.impl.BookMapperImpl;
import me.fairygel.fbook.repository.BookCrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class BookService {
    private static final String NO_BOOK_WITH_ID = "No book with id = ";

    private final BookMapperImpl mapper;
    private final BookAutomation bookAutomation;
    private final BookCrudRepository bookRepository;

    @SneakyThrows
    public void create(CreateBookDTO bookDTO, MultipartFile cover) {
        Book book = mapper.createBookDtoToBook(bookDTO);

        if (cover != null)
            book.setCover(cover.getBytes());

        bookRepository.save(book);
    }
    public BookFullViewDTO read(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NO_BOOK_WITH_ID + id));

        return mapper.bookToBookFullViewDto(book);
    }
    public BookCoverDTO getCover(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NO_BOOK_WITH_ID + id));

        return mapper.bookToBookCoverDto(book);
    }
    @SneakyThrows
    public BookFullViewDTO update(long id, UpdateBookDTO bookDTO, MultipartFile cover) {
        Book book = mapper.updateBookDtoToBook(bookDTO);

        bookAutomation.automate(book);
        if (cover != null) book.setCover(cover.getBytes());

        Book updatedBook = bookRepository.updateById(id, book)
                .orElseThrow(() -> new EntityNotFoundException(NO_BOOK_WITH_ID + id));

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
