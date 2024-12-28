package me.fairygel.fbook.dto.grade;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateGradeDTO {
    @NotNull(message = "book id must not be empty")
    private Long bookId;

    @NotNull(message = "your grade must not be empty")
    @Min(value = 1, message = "your grade must be between 1 and 5")
    @Max(value = 5, message = "your grade must be between 1 and 5")
    private Byte rating;

    @Size(min = 1, message = "comment must have at least one character")
    private String comment;
}
