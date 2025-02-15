package me.fairygel.fbook.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "book_covers", schema = "fbook")
public class BookCover {
    @Id
    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "cover", nullable = false)
    private byte[] cover;

    @Column(name = "image_type", nullable = false)
    private String imageType;
}
