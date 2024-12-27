package me.fairygel.fbook.dto.grade;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateGradeDTO {
    @NotNull
    private Long bookId;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Byte rating;

    @NotBlank
    private String comment;
}
