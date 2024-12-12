package me.fairygel.fbook.dto.book;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CreateBookDTO {
    private Long id = 0L;
    private String name = "";
    private Long authorId = 0L;
    private Set<Long> genreIds = new HashSet<>();
    private String annotation = "";
    private Long bookTypeId = 0L;
}
