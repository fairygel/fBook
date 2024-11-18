package me.fairygel.fbook.dto.book;

import lombok.Data;

import java.util.List;

@Data
public class CreateBookDTO {
    private Long id;
    private String name;
    private Long authorId;
    private List<Long> genreIds;
    private String annotation;
    private Long bookTypeId;
}
