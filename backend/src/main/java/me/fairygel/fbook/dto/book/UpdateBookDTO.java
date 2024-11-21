package me.fairygel.fbook.dto.book;

import lombok.Data;

import java.util.List;

@Data
public class UpdateBookDTO {
    private Long id;
    private String name;
    private Long authorId;
    private List<Long> genreIds;
    private Long bookStatusId;
    private String startedReadDate;
    private String endedReadDate;
    private String annotation;
    private Long bookTypeId;
    private Long gradeId;
    private Byte gradeRating;
    private String gradeComment;
}
