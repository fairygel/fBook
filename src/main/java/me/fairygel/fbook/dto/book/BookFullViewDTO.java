package me.fairygel.fbook.dto.book;

import lombok.Data;

import java.util.List;

@Data
public class BookFullViewDTO {
    private Long id;
    private String name;
    private String authorFirstName;
    private String authorLastName;
    private List<String> genres;
    private String bookStatus;
    private String startedReadDate;
    private String endedReadDate;
    private String annotation;
    private String bookType;
    private Byte gradeRating;
    private String gradeComment;
}
