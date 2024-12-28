package me.fairygel.fbook.dto.author;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import me.fairygel.fbook.util.validation.OnCreateGroup;

@Data
public class AuthorDTO {
    @NotNull(groups = OnCreateGroup.class)
    @Size(min = 1)
    private String firstName;

    @Size(min = 1)
    private String lastName;
}
