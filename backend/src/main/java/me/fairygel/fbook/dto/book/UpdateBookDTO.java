package me.fairygel.fbook.dto.book;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateBookDTO {
    @Size(min = 1)
    private String name;

    private Long authorId;

    private Set<Long> genreIds;

    private Short bookStatusId;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    private String startedReadDate;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    private String endedReadDate;

    @Size(min = 1)
    private String annotation;

    private Short bookTypeId;
}
