package me.fairygel.fbook.dto.genre;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GenreDTO {
    @NotNull
    private String genre;
}
