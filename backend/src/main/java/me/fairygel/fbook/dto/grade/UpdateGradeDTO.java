package me.fairygel.fbook.dto.grade;

import lombok.Data;

@Data
public class UpdateGradeDTO {
    private Byte rating;
    private String comment;
}
