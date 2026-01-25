package me.fairygel.fbook.dto.grade;

import jakarta.validation.constraints.*;
import lombok.Data;
import me.fairygel.fbook.util.validation.OnCreateGroup;
import me.fairygel.fbook.util.validation.OnUpdateGroup;

@Data
public class GradeDTO {
    @NotNull(groups = { OnCreateGroup.class }, message = "Book ID must not be empty when creating a grade")
    @Null(groups = { OnUpdateGroup.class }, message = "Book ID cannot be changed when updating a grade")
    private Long bookId;

    @NotNull(groups = { OnUpdateGroup.class, OnCreateGroup.class }, message = "Rating must not be empty")
    @Min(groups = { OnUpdateGroup.class, OnCreateGroup.class }, value = 1, message = "Rating must be between 1 and 5")
    @Max(groups = { OnUpdateGroup.class, OnCreateGroup.class }, value = 5, message = "Rating must be between 1 and 5")
    private Byte rating;

    private String comment;
}
