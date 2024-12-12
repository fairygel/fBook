package me.fairygel.fbook.dto.book;

import lombok.Data;

import java.util.Set;

@Data
public class UpdateBookDTO {
    private Long id;
    private String name;
    private Long authorId;
    private Set<Long> genreIds;
    private Long bookStatusId;
    private String startedReadDate;
    private String endedReadDate;
    private String annotation;
    private Long bookTypeId;
}
