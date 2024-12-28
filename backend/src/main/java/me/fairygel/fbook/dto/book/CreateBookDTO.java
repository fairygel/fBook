package me.fairygel.fbook.dto.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CreateBookDTO {
    @NotNull(message = "book name must be not empty")
    @Size(min = 1, message = "book name must contain at least one character")
    private String name;

    @NotNull(message = "book must have an author")
    private Long authorId;

    @NotEmpty(message = "book must have at least one genre")
    private Set<Long> genreIds = new HashSet<>();

    @Size(min = 1, message = "book annotation must contain at least one character")
    private String annotation;

    @NotNull(message = "book type can not be empty")
    private Short bookTypeId;
}
