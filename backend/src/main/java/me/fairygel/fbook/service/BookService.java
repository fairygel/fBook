package me.fairygel.fbook.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import me.fairygel.fbook.dto.book.*;
import me.fairygel.fbook.entity.Book;
import me.fairygel.fbook.util.BookHelper;
import me.fairygel.fbook.util.mapper.impl.BookMapperImpl;
import me.fairygel.fbook.repository.BookCrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class BookService {
    private static final String NO_BOOK_WITH_ID = "No book with ID = ";

    private final BookMapperImpl bookMapper;
    private final BookCoverService coverService;
    private final BookHelper bookHelper;
    private final BookCrudRepository bookRepository;
    private final Validator validator;

    @SneakyThrows
    public IndexBookViewDTO create(BookDTO bookDTO, MultipartFile cover) {
        Book book = bookMapper.bookDtoToBook(bookDTO, true);
        Book savedBook = bookRepository.save(book);
        coverService.createCover(savedBook, cover);
        return bookMapper.bookToIndexBookViewDto(savedBook);
    }
    public BookFullViewDTO read(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NO_BOOK_WITH_ID + id));

        return bookMapper.bookToBookFullViewDto(book);
    }

    @SneakyThrows
    public BookFullViewDTO update(long id, BookDTO bookDTO, MultipartFile cover) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(NO_BOOK_WITH_ID + id));

        Book book = bookMapper.bookDtoToBook(bookDTO, existingBook, false);

        bookHelper.automate(book, existingBook.getBookStatus());

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

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
