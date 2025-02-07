package me.fairygel.fbook.dto.grade;

import lombok.Data;
import me.fairygel.fbook.dto.book.IndexBookViewDTO;

@Data
public class GradeFullViewDTO {
    private IndexBookViewDTO book;
    private Byte rating;
    private String comment;
}
