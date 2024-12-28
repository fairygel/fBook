package me.fairygel.fbook.dto.grade;

import lombok.Data;
import me.fairygel.fbook.dto.book.IndexBookViewDTO;

@Data
public class GradePreviewDTO {
    private IndexBookViewDTO book;
    private Byte rating;
    private String comment;
}
