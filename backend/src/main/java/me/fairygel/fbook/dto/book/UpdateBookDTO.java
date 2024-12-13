package me.fairygel.fbook.dto.book;

import lombok.Data;

import java.util.Set;

@Data
public class UpdateBookDTO {
    private Long id;
    private String name;
    private Long authorId;
    private Set<Long> genreIds;
    private Short bookStatusId;
    private String startedReadDate;
    private String endedReadDate;
    private String annotation;
    private Short bookTypeId;
}
