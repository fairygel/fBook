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

    private final BookMapperImpl bookMapper;
    private final BookCoverService coverService;
    private final BookAutomation bookAutomation;
    private final BookCrudRepository bookRepository;

    @SneakyThrows
    public Book create(CreateBookDTO bookDTO) {
        Book book = bookMapper.createBookDtoToBook(bookDTO);

        return bookRepository.save(book);
    }
    public BookFullViewDTO read(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NO_BOOK_WITH_ID + id));

        return bookMapper.bookToBookFullViewDto(book);
    }

    @SneakyThrows
    public BookFullViewDTO update(long id, UpdateBookDTO bookDTO, MultipartFile cover) {
        Book book = bookMapper.updateBookDtoToBook(bookDTO);

        bookAutomation.automate(book);

        Book updatedBook = bookRepository.updateById(id, book)
                .orElseThrow(() -> new EntityNotFoundException(NO_BOOK_WITH_ID + id));

        coverService.updateCover(updatedBook, cover);

        return bookMapper.bookToBookFullViewDto(updatedBook);
    }
    public void delete(long id) {
        bookRepository.deleteById(id);
    }
    public Set<IndexBookViewDTO> index() {
        Set<IndexBookViewDTO> books = new HashSet<>();

        bookRepository.findAll().forEach(book -> books.add(bookMapper.bookToIndexBookViewDto(book)));
        return books;
    }
}
