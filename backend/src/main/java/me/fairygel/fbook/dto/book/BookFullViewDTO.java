package me.fairygel.fbook.dto.book;

import lombok.Data;
import me.fairygel.fbook.dto.BookStatusDTO;
import me.fairygel.fbook.dto.BookTypeDTO;
import me.fairygel.fbook.dto.GenreDTO;
import me.fairygel.fbook.dto.author.AuthorIndexViewDTO;
import me.fairygel.fbook.dto.grade.GradePreviewDTO;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Set;

@Data
public class BookFullViewDTO {
    private Long id;
    private String name;
    private AuthorIndexViewDTO author;
    private Set<GenreDTO> genres;
    private BookStatusDTO bookStatus;
    private LocalDate startedReadDate;
    private LocalDate endedReadDate;
    private String annotation;
    private BookTypeDTO bookType;
    private GradePreviewDTO grade;
}
