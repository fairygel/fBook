package me.fairygel.fbook.mapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.fairygel.fbook.dao.*;
import me.fairygel.fbook.dto.book.BookFullViewDTO;
import me.fairygel.fbook.dto.book.CreateBookDTO;
import me.fairygel.fbook.dto.book.IndexBookViewDTO;
import me.fairygel.fbook.dto.book.UpdateBookDTO;
import me.fairygel.fbook.entity.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class BookMapper {
    private AuthorDAO authorDAO;
    private BookStatusReadOnlyDAO bookStatusDAO;
    private BookTypeReadOnlyDAO bookTypeDAO;
    private GenreDAO genreDAO;

    public Book createBookDtoToBook(CreateBookDTO bookDTO) {
        Book book = new Book();

        Author author = authorDAO.read(bookDTO.getAuthorId());
        List<Genre> genres = genreDAO.readAll(bookDTO.getGenreIds());
        BookStatus bookStatus = bookStatusDAO.read(0L);
        BookType bookType = bookTypeDAO.read(bookDTO.getBookTypeId());

        book.setId(bookDTO.getId());
        book.setName(bookDTO.getName());
        book.setAuthor(author);
        book.setGenres(genres);
        book.setBookStatus(bookStatus);
        book.setAnnotation(bookDTO.getAnnotation());
        book.setBookType(bookType);
        book.setGrade(new Grade());

        return book;
    }

    public Book updateBookDtoToBook(UpdateBookDTO bookDTO) {
        Book book = new Book();

        Author author = getAuthor(bookDTO);
        List<Genre> genres = getGenres(bookDTO);
        BookStatus bookStatus = getBookStatus(bookDTO);
        BookType bookType = getBookType(bookDTO);
        Date startedDate = stringToDate(bookDTO.getStartedReadDate());
        Date endedDate = stringToDate(bookDTO.getEndedReadDate());

        book.setId(bookDTO.getId());
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
        else return authorDAO.read(bookDTO.getAuthorId());
    }

    private List<Genre> getGenres(UpdateBookDTO bookDTO) {
        if (bookDTO.getGenreIds() == null) return null;
        else return genreDAO.readAll(bookDTO.getGenreIds());
    }

    private BookStatus getBookStatus(UpdateBookDTO bookDTO) {
        if (bookDTO.getBookStatusId() == null) return null;
        else return bookStatusDAO.read(bookDTO.getBookStatusId());
    }

    private BookType getBookType(UpdateBookDTO bookDTO) {
        if (bookDTO.getBookTypeId() == null) return null;
        else return bookTypeDAO.read(bookDTO.getBookTypeId());
    }

    private Date stringToDate(String str) {
        if (str.isEmpty()) return null;
        try {
            return Date.valueOf(str);
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
        bookDTO.setGenres(book.getGenres().stream().map(Genre::getName).toList());
        bookDTO.setBookStatus(book.getBookStatus().getStatus());
        bookDTO.setStartedReadDate(dateToString(book.getStartedReadDate()));
        bookDTO.setEndedReadDate(dateToString(book.getEndedReadDate()));
        bookDTO.setAnnotation(book.getAnnotation());
        bookDTO.setBookType(book.getBookType().getType());
        bookDTO.setGradeRating(book.getGrade().getRating());
        bookDTO.setGradeComment(book.getGrade().getComment());

        return bookDTO;
    }

    private String dateToString(Date date) {
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
