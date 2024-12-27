package me.fairygel.fbook.dto.grade;

import lombok.Data;

@Data
public class GradePreviewDTO {
    private Long bookId;
    private String bookName;
    private Byte rating;
    private String comment;
}
