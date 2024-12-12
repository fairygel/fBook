package me.fairygel.fbook.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "grades", schema = "fbook")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grade_id_seq")
    @SequenceGenerator(name = "grade_id_seq", sequenceName = "grade_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "rating", nullable = false)
    private Short rating;

    @Column(name = "comment", length = Integer.MAX_VALUE)
    private String comment;

    @OneToMany(mappedBy = "grade")
    private Set<Book> books = new LinkedHashSet<>();

}