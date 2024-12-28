package me.fairygel.fbook.dto.genre;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GenreDTO {
    @Size(min = 1)
    private String genre;
}
