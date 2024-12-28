package me.fairygel.fbook.dto.book.type;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookTypeDTO {
    @NotNull
    @Size(min = 1)
    private String type;
}
