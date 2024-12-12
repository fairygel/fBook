package me.fairygel.fbook.dto.book;

import lombok.Data;

import java.util.Set;

@Data
public class BookFullViewDTO {
    private Long id;
    private String name;
    private String authorFirstName;
    private String authorLastName;
    private Set<String> genres;
    private String bookStatus;
    private String startedReadDate;
    private String endedReadDate;
    private String annotation;
    private String bookType;
    private Short gradeRating;
    private String gradeComment;
}
