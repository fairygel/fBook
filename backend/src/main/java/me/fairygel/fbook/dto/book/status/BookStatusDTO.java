package me.fairygel.fbook.dto.book.status;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookStatusDTO {
    @NotNull
    @Size(min = 1)
    private String status;
}
