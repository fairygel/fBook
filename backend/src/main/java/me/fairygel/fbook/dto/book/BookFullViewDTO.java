package me.fairygel.fbook.dto.book;

import lombok.Data;
import me.fairygel.fbook.dto.author.AuthorIndexViewDTO;
import me.fairygel.fbook.dto.book.status.BookStatusIndexViewDTO;
import me.fairygel.fbook.dto.book.type.BookTypeIndexViewDTO;
import me.fairygel.fbook.dto.genre.GenreIndexViewDTO;
import me.fairygel.fbook.dto.grade.GradeFullViewDTO;
import me.fairygel.fbook.dto.grade.GradePreviewDTO;

import java.util.Set;

@Data
public class BookFullViewDTO {
    private Long id;
    private String name;
    private AuthorIndexViewDTO author;
    private Set<GenreIndexViewDTO> genres;
    private BookStatusIndexViewDTO bookStatus;
    private String startedReadDate;
    private String endedReadDate;
    private String annotation;
    private BookTypeIndexViewDTO bookType;
    private GradePreviewDTO grade;
}
