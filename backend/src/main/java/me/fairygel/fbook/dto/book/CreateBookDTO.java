package me.fairygel.fbook.dto.book;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CreateBookDTO {
    @NotNull
    @Size(min = 1)
    private String name;

    @NotNull
    private Long authorId;

    @NotNull
    private Set<Long> genreIds = new HashSet<>();

    private String annotation = "";

    @NotNull
    private Short bookTypeId;
}
