package me.fairygel.fbook.dto.grade;

import lombok.Data;

@Data
public class CreateGradeDTO {
    private Long bookId;
    private Byte rating;
    private String comment;
}
