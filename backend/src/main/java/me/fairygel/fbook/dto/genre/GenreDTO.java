package me.fairygel.fbook.dto.genre;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GenreDTO {
    @Size(min = 1, message = "genre name must have at least one character")
    private String genre;
}
