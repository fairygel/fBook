package me.fairygel.fbook.dto.book;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateBookDTO {
    private Long id = 0L;
    private String name = "";
    private Long authorId = 0L;
    private List<Long> genreIds = new ArrayList<>();
    private String annotation = "";
    private Long bookTypeId = 0L;
}
