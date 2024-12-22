package me.fairygel.fbook.dto.book;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CreateBookDTO {
    private String name = "";
    private Long authorId = 0L;
    private Set<Long> genreIds = new HashSet<>();
    private String annotation = "";
    private Short bookTypeId = 0;
}
