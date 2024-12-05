package me.fairygel.fbook.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Grade {
    private Long id;
    private Long bookId;
    private Byte rating;
    private String comment;
}
