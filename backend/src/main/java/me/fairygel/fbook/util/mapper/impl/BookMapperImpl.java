package me.fairygel.fbook.util.mapper.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.fairygel.fbook.dto.book.BookFullViewDTO;
import me.fairygel.fbook.dto.book.CreateBookDTO;
import me.fairygel.fbook.dto.book.IndexBookViewDTO;
import me.fairygel.fbook.dto.book.UpdateBookDTO;
import me.fairygel.fbook.entity.*;
import me.fairygel.fbook.util.mapper.BookMapper;
import me.fairygel.fbook.repository.AuthorCrudRepository;
import me.fairygel.fbook.repository.BookStatusReadOnlyRepository;
import me.fairygel.fbook.repository.BookTypeReadOnlyRepository;
import me.fairygel.fbook.repository.GenreCrudRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@Slf4j
@Component
@AllArgsConstructor
public class BookMapperImpl implements BookMapper {
    private AuthorCrudRepository authorRepository;
    private BookStatusReadOnlyRepository bookStatusRepository;
    private BookTypeReadOnlyRepository bookTypeRepository;
    private GenreCrudRepository genreRepository;

    // --- DTO To Book ---

    @Override
    public Book createBookDtoToBook(CreateBookDTO bookDTO) {
        Book book = new Book();

        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("No author with id = " + bookDTO.getAuthorId()));

        Set<Genre> genres = new HashSet<>();

        for (Long genreId : bookDTO.getGenreIds()) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new EntityNotFoundException("No genre with id = " + genreId));
            genres.add(genre);
        }

        BookStatus bookStatus = bookStatusRepository.findById((short) 0).orElseThrow(IllegalAccessError::new);
        BookType bookType = bookTypeRepository.findById(bookDTO.getBookTypeId())
                .orElseThrow(() -> new EntityNotFoundException("No book type with id = " + bookDTO.getBookTypeId()));

        book.setName(bookDTO.getName());
        book.setAuthor(author);
        book.setGenres(genres);
        book.setBookStatus(bookStatus);
        book.setAnnotation(bookDTO.getAnnotation());
        book.setBookType(bookType);

        return book;
    }

    @Override
    public Book updateBookDtoToBook(UpdateBookDTO bookDTO) {
        Book book = new Book();

        Author author = getAuthor(bookDTO);
        Set<Genre> genres = getGenres(bookDTO);
        BookStatus bookStatus = getBookStatus(bookDTO);
        BookType bookType = getBookType(bookDTO);
        LocalDate startedDate = stringToDate(bookDTO.getStartedReadDate());
        LocalDate endedDate = stringToDate(bookDTO.getEndedReadDate());

        book.setName(bookDTO.getName());
        book.setAuthor(author);
        book.setGenres(genres.isEmpty() ? null : genres);
        book.setBookStatus(bookStatus);
        book.setStartedReadDate(startedDate);
        book.setEndedReadDate(endedDate);
        book.setAnnotation(bookDTO.getAnnotation());
        book.setBookType(bookType);

        return book;
    }

    // --- Book To DTO ---

    @Override
    public BookFullViewDTO bookToBookFullViewDto(Book book) {
        BookFullViewDTO bookDTO = new BookFullViewDTO();

        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());
        bookDTO.setAuthorFirstName(book.getAuthor().getFirstName());
        bookDTO.setAuthorLastName(book.getAuthor().getLastName());
        bookDTO.setGenres(book.getGenres().stream().map(Genre::getName).collect(Collectors.toSet()));
        bookDTO.setBookStatus(book.getBookStatus().getName());
        bookDTO.setStartedReadDate(dateToString(book.getStartedReadDate()));
        bookDTO.setEndedReadDate(dateToString(book.getEndedReadDate()));
        bookDTO.setAnnotation(book.getAnnotation());
        bookDTO.setBookType(book.getBookType().getName());

        return bookDTO;
    }
    @Override
    public IndexBookViewDTO bookToIndexBookViewDto(Book book) {
        IndexBookViewDTO bookDTO = new IndexBookViewDTO();

        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());

        return bookDTO;
    }

    // --- Helpful Stuff ---

    private Author getAuthor(UpdateBookDTO bookDTO) {
        Long authorId = bookDTO.getAuthorId();

        if (authorId == null) return null;

        else return authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("No author with id = " + authorId));
    }

    private Set<Genre> getGenres(UpdateBookDTO bookDTO) {
        if (bookDTO.getGenreIds() == null) return Collections.emptySet();

        Set<Genre> genres = new HashSet<>();

        for (Long genreId : bookDTO.getGenreIds()) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new EntityNotFoundException("No genre with id = " + genreId));
            genres.add(genre);
        }

        return genres;
    }

    private BookStatus getBookStatus(UpdateBookDTO bookDTO) {
        Short statusId = bookDTO.getBookStatusId();

        if (statusId == null) return null;

        else return bookStatusRepository.findById(statusId)
                .orElseThrow(() -> new EntityNotFoundException("No book status with id = " + statusId));
    }

    private BookType getBookType(UpdateBookDTO bookDTO) {
        Short typeId = bookDTO.getBookTypeId();

        if (typeId == null) return null;

        else return bookTypeRepository.findById(typeId)
                .orElseThrow(() -> new EntityNotFoundException("No book type with id = " + typeId));
    }

    @SneakyThrows
    private LocalDate stringToDate(String str) {
        if (str == null || str.isEmpty()) return null;
        try {
            return LocalDate.parse(str);
        } catch (DateTimeParseException e) {
            throw new DataFormatException(e.getMessage());
        }
    }
    private String dateToString(LocalDate date) {
        if (date == null) return "";
        return date.toString();
    }
}
