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
        BookStatus bookStatus = bookStatusDAO.read(1L);
        BookType bookType = bookTypeDAO.read(bookDTO.getBookTypeId());

        book.setId(bookDTO.getId());
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

        Author author = authorDAO.read(bookDTO.getAuthorId());
        List<Genre> genres = genreDAO.readAll(bookDTO.getGenreIds());
        BookStatus bookStatus = bookStatusDAO.read(1L);
        BookType bookType = bookTypeDAO.read(bookDTO.getBookTypeId());
        Date startedDate = stringToDate(bookDTO.getStartedReadDate());
        Date endedDate = stringToDate(bookDTO.getEndedReadDate());

        Grade grade = new Grade();
        grade.setId(bookDTO.getGradeId());
        grade.setComment(bookDTO.getGradeComment());
        grade.setRating(bookDTO.getGradeRating());

        book.setId(bookDTO.getId());
        book.setName(bookDTO.getName());
        book.setAuthor(author);
        book.setGenres(genres);
        book.setBookStatus(bookStatus);
        book.setStartedReadDate(startedDate);
        book.setEndedReadDate(endedDate);
        book.setAnnotation(bookDTO.getAnnotation());
        book.setBookType(bookType);
        book.setGrade(grade);

        return book;
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
        bookDTO.setStartedReadDate(book.getStartedReadDate().toString());
        bookDTO.setEndedReadDate(book.getEndedReadDate().toString());
        bookDTO.setAnnotation(book.getAnnotation());
        bookDTO.setBookType(book.getBookType().getType());
        bookDTO.setGradeRating(book.getGrade().getRating());
        bookDTO.setGradeComment(book.getGrade().getComment());

        return bookDTO;
    }



    public IndexBookViewDTO bookToIndexBookViewDto(Book book) {
        IndexBookViewDTO bookDTO = new IndexBookViewDTO();

        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());

        return bookDTO;
    }
}
