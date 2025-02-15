package me.fairygel.fbook.dto.book;

import lombok.Data;

@Data
public class BookCoverDTO {
    private String imageType;
    private byte[] cover;
}
