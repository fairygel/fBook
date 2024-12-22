package me.fairygel.fbook.mapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.fairygel.fbook.dto.book.BookFullViewDTO;
import me.fairygel.fbook.dto.book.CreateBookDTO;
import me.fairygel.fbook.dto.book.IndexBookViewDTO;
import me.fairygel.fbook.dto.book.UpdateBookDTO;
import me.fairygel.fbook.entity.*;
import me.fairygel.fbook.repository.AuthorCrudRepository;
import me.fairygel.fbook.repository.BookStatusReadOnlyRepository;
import me.fairygel.fbook.repository.BookTypeReadOnlyRepository;
import me.fairygel.fbook.repository.GenreCrudRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class BookMapper {
    private AuthorCrudRepository authorRepository;
    private BookStatusReadOnlyRepository bookStatusRepository;
    private BookTypeReadOnlyRepository bookTypeRepository;
    private GenreCrudRepository genreRepository;

    public Book createBookDtoToBook(CreateBookDTO bookDTO) {
        Book book = new Book();

        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElse(authorRepository.findById(0L).orElseThrow(IllegalAccessError::new));

        Set<Genre> genres = new HashSet<>();

        for (Long genreId : bookDTO.getGenreIds()) {
            Genre genre = genreRepository.findById(genreId)
                   .orElse(genreRepository.findById(0L).orElseThrow(IllegalAccessError::new));
            genres.add(genre);
        }

        BookStatus bookStatus = bookStatusRepository.findById((short) 0).orElseThrow(IllegalAccessError::new);
        BookType bookType = bookTypeRepository.findById(bookDTO.getBookTypeId())
                .orElse(bookTypeRepository.findById((short) 0).orElseThrow(IllegalAccessError::new));

        book.setName(bookDTO.getName());
        book.setAuthor(author);
        book.setGenres(genres);
        book.setBookStatus(bookStatus);
        book.setAnnotation(bookDTO.getAnnotation());
        book.setBookType(bookType);

        return book;
    }

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
        book.setGenres(genres);
        book.setBookStatus(bookStatus);
        book.setStartedReadDate(startedDate);
        book.setEndedReadDate(endedDate);
        book.setAnnotation(bookDTO.getAnnotation());
        book.setBookType(bookType);

        return book;
    }

    private Author getAuthor(UpdateBookDTO bookDTO) {
        if (bookDTO.getAuthorId() == null) return null;
        else return authorRepository.findById(bookDTO.getAuthorId()).orElseThrow(IllegalAccessError::new);
    }

    private Set<Genre> getGenres(UpdateBookDTO bookDTO) {
        if (bookDTO.getGenreIds() == null) return null;

        Set<Genre> genres = new HashSet<>();

        for (Long genreId : bookDTO.getGenreIds()) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(IllegalAccessError::new);
            genres.add(genre);
        }

        return genres;
    }

    private BookStatus getBookStatus(UpdateBookDTO bookDTO) {
        if (bookDTO.getBookStatusId() == null) return null;
        else return bookStatusRepository.findById(bookDTO.getBookStatusId()).orElseThrow(IllegalAccessError::new);
    }

    private BookType getBookType(UpdateBookDTO bookDTO) {
        if (bookDTO.getBookTypeId() == null) return null;
        else return bookTypeRepository.findById(bookDTO.getBookTypeId()).orElseThrow(IllegalAccessError::new);
    }

    private LocalDate stringToDate(String str) {
        if (str == null || str.isEmpty()) return null;
        try {
            return LocalDate.parse(str);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
    }

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

    private String dateToString(LocalDate date) {
        if (date == null) return "";
        return date.toString();
    }

    public IndexBookViewDTO bookToIndexBookViewDto(Book book) {
        IndexBookViewDTO bookDTO = new IndexBookViewDTO();

        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());

        return bookDTO;
    }
}
