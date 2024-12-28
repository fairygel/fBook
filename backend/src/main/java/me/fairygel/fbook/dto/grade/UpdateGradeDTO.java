package me.fairygel.fbook.dto.grade;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateGradeDTO {
    @Min(value = 1, message = "your grade must be between 1 and 5")
    @Max(value = 5, message = "your grade must be between 1 and 5")
    private Byte rating;

    @Size(min = 1, message = "comment must have at least one character")
    private String comment;
}
