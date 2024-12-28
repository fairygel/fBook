package me.fairygel.fbook.dto.grade;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateGradeDTO {
    @NotNull
    private Long bookId;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Byte rating;

    @Size(min = 1)
    private String comment;
}
