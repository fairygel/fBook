package me.fairygel.fbook.dto.book;

import jakarta.validation.constraints.*;
import lombok.Data;
import me.fairygel.fbook.util.validation.OnCreateGroup;
import me.fairygel.fbook.util.validation.OnUpdateGroup;

import java.time.LocalDate;
import java.util.Set;

@Data
public class BookDTO {
    @NotNull(groups =  { OnCreateGroup.class, OnUpdateGroup.class }, message = "Book name must not be empty")
    @Size(groups = { OnCreateGroup.class, OnUpdateGroup.class },
            min = 1, message = "Book name must contain at least one character")
    private String name;

    private Long authorId;
    private Set<Long> genreIds;
    private String annotation;
    private Short bookTypeId;

    @Null(groups = { OnCreateGroup.class }, message = "Book status cannot be set when creating a book")
    private Short bookStatusId;

    @Null(groups = { OnCreateGroup.class }, message = "Started read date cannot be set when creating a book")
    @PastOrPresent(groups = { OnUpdateGroup.class }, message = "Started read date cannot be in the future")
    private LocalDate startedReadDate;

    @Null(groups = { OnCreateGroup.class }, message = "Ended read date cannot be set when creating a book")
    @PastOrPresent(groups = { OnUpdateGroup.class }, message = "Ended read date cannot be in the future")
    private LocalDate endedReadDate;


}
