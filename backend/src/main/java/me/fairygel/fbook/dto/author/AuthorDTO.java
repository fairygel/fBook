package me.fairygel.fbook.dto.author;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import me.fairygel.fbook.util.validation.OnCreateGroup;
import me.fairygel.fbook.util.validation.OnUpdateGroup;

@Data
public class AuthorDTO {
    @NotNull(groups = OnCreateGroup.class, message = "when creating, author first name must exist")
    @Size(min = 1, groups = {OnCreateGroup.class, OnUpdateGroup.class}, message = "author first name should have at least one character")
    private String firstName;

    @Size(min = 1, groups = {OnCreateGroup.class, OnUpdateGroup.class}, message = "author last name should have at least one character")
    private String lastName;
}
