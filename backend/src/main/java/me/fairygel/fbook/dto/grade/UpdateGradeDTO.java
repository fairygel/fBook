package me.fairygel.fbook.dto.grade;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateGradeDTO {
    @Min(value = 1)
    @Max(value = 5)
    private Byte rating;

    private String comment;
}
