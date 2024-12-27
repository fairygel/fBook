package me.fairygel.fbook.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthorDTO {
    @NotNull
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
