package me.fairygel.fbook.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "book_types", schema = "fbook")
public class BookType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_type_id_seq")
    @SequenceGenerator(name = "book_type_id_seq", sequenceName = "book_type_id_seq")
    @Column(name = "id", nullable = false)
    private Short id;

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @OneToMany(mappedBy = "bookType")
    private Set<Book> books = new LinkedHashSet<>();

}