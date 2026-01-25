package me.fairygel.fbook.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;
import me.fairygel.fbook.util.validation.OnCreateGroup;
import me.fairygel.fbook.util.validation.OnUpdateGroup;

@Data
public class GenreDTO {
    @Null(groups = { OnCreateGroup.class, OnUpdateGroup.class}, message = "ID must not be provided when creating or updating a genre")
    private Long id;

    @NotNull(groups = { OnCreateGroup.class, OnUpdateGroup.class}, message = "Genre name must not be empty")
    @Size(groups = { OnCreateGroup.class, OnUpdateGroup.class}, min = 1, message = "Genre name must have at least one character")
    private String genre;
}
