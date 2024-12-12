package me.fairygel.fbook.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "book_statuses", schema = "fbook")
public class BookStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_status_id_seq")
    @SequenceGenerator(name = "book_status_id_seq", sequenceName = "book_status_id_seq")
    @Column(name = "id", nullable = false)
    private Short id;

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @OneToMany(mappedBy = "bookStatus")
    private Set<Book> books = new LinkedHashSet<>();

}