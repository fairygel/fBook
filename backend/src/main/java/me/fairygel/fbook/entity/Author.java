package me.fairygel.fbook.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "authors", schema = "fbook")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    @SequenceGenerator(name = "author_id_seq", sequenceName = "author_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "firstname", nullable = false, length = Integer.MAX_VALUE)
    private String firstName;

    @Column(name = "lastname", length = Integer.MAX_VALUE)
    private String lastName;

    @OneToMany(mappedBy = "author")
    private Set<Book> books = new LinkedHashSet<>();

}