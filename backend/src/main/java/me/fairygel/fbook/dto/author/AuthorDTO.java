package me.fairygel.fbook.dto.author;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;
import me.fairygel.fbook.util.validation.OnCreateGroup;
import me.fairygel.fbook.util.validation.OnUpdateGroup;

@Data
public class AuthorDTO {
    @Null(groups = { OnCreateGroup.class, OnUpdateGroup.class}, message = "ID must not be provided when creating or updating an author")
    private Long id;

    @NotNull(groups = OnCreateGroup.class, message = "Author first name must not be empty when creating")
    @Size(min = 1, groups = {OnCreateGroup.class, OnUpdateGroup.class}, message = "Author first name must have at least one character")
    private String firstName;

    @Size(min = 1, groups = {OnCreateGroup.class, OnUpdateGroup.class}, message = "Author last name must have at least one character")
    private String lastName;
}
